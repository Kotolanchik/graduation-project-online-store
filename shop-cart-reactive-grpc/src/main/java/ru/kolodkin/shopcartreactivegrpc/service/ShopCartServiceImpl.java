package ru.kolodkin.shopcartreactivegrpc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.shopcartreactivegrpc.bean.ProductInfo;
import ru.kolodkin.shopcartreactivegrpc.bean.ShopCartDTO;
import ru.kolodkin.shopcartreactivegrpc.entity.ShopCart;
import ru.kolodkin.shopcartreactivegrpc.repository.ShopCartRepository;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopCartServiceImpl implements ShopCartService {
    private final ShopCartRepository repository;
    private final Client client;
    private final TransactionalOperator operator;

    @Override
    public Mono<ShopCart> addProductInShopCart(Long userId, Long productId) {
        return repository.save(ru.kolodkin.shopcartreactivegrpc.entity.ShopCart.builder()
                        .productId(productId)
                        .userId(userId)
                        .quantity(1L)
                        .build())
                .doOnSuccess(success -> log.info("Товар добавлен в корзину пользователя userId: {}", success.getUserId()));
    }

    @Override
    public Flux<ShopCartDTO> getAllShopCart(Long userId) {
        return shopCartListToDTOList(repository.findAllByUserId(userId))
                .doOnError(error -> log.error(error.getMessage()));
    }

    @Override
    public Mono<ShopCart> updateProductQuantityInShopCart(Long userId, Long productId, Long quantity) {
        return repository.updateQuantityByUserIdAndProductId(userId, productId, quantity)
                .doOnError(error -> log.error("Ошибка при сохранении в бд: " + error.getMessage()))
                .doOnSuccess(success -> log.info("Информация о количестве товаров успешно обновлена"));
    }

    @Override
    public Mono<Mono<ShopCartDTO>> getShopCartProduct(Long userId, Long productId) {
        return shopCartToDTO(repository.findByUserIdAndProductId(userId, productId))
                .doOnError(error -> log.error("getShopCartProduct SERVICE: " + error.getMessage()))
                .doOnSuccess(success -> log.info("Информация о товарове получена"));
    }

    @Override
    public Mono<Void> deleteProductFromShopCart(Long userId, Long productId) {
        return repository.deleteByUserIdAndProductId(userId, productId)
                .doOnError(error -> log.error("deleteProductFromShopCart SERVICE: " + error.getMessage()))
                .doOnSuccess(success -> log.info("Товар: {} пользователя: {} удалён", productId, userId));

    }

    @Override
    public Mono<Void> deleteProductsFromShopCart(Long userId) {
        return repository.deleteAllByUserId(userId)
                .doOnError(error -> log.error(error.getMessage()))
                .doOnSuccess(success -> log.info("Удалены все товары пользователя: {}", userId));
    }

    private Flux<ShopCartDTO> shopCartListToDTOList(Flux<ShopCart> shopCartFlux) {
        return client.getProductsByIds(shopCartFlux.map(ShopCart::getProductId))
                .doOnError(error -> log.error("shopCartListToDTOList SERVICE товары из каталога: " + error.getMessage()))
                .collectMap(ProductInfo::getId, productInfo -> productInfo)
                .flatMapMany(productMap -> shopCartFlux
                        .flatMap(shopCart -> createShopCartDTO(shopCart, productMap))
                        .filter(Objects::nonNull)
                );
    }

    private Mono<Mono<ShopCartDTO>> shopCartToDTO(Mono<ShopCart> shopCartMono) {
        return shopCartMono
                .flatMap(shopCart -> client.getProductById(shopCart.getProductId())
                        .zipWith(Mono.just(shopCart), this::createShopCartDTO)
                ).doOnError(error -> log.error("ERROR REQUEST IN CATALOG: " + error.getMessage()));
    }

    private Mono<ShopCartDTO> createShopCartDTO(ProductInfo productInfo, ShopCart shopCart) {
        return Mono.just(ShopCartDTO.builder()
                .product(productInfo)
                .userId(shopCart.getUserId())
                .quantity(shopCart.getQuantity())
                .build());
    }

    private Mono<ShopCartDTO> createShopCartDTO(ShopCart shopCart, Map<Long, ProductInfo> productMap) {
        ProductInfo productInfo = productMap.get(shopCart.getProductId());
        if (productInfo != null) {
            return createShopCartDTO(productInfo, shopCart);
        } else {
            return Mono.empty();
        }
    }
}
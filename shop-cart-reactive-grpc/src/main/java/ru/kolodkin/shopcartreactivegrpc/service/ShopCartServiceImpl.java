package ru.kolodkin.shopcartreactivegrpc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.shopcartreactivegrpc.bean.ProductInfo;
import ru.kolodkin.shopcartreactivegrpc.bean.ShopCartDTO;
import ru.kolodkin.shopcartreactivegrpc.entity.ShopCart;
import ru.kolodkin.shopcartreactivegrpc.repository.ShopCartRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopCartServiceImpl implements ShopCartService {
    private final ShopCartRepository repository;
    private final Client client;

    @Transactional
    @Override
    public Mono<ShopCart> addProductInShopCart(Long userId, Long productId) {
        return repository.save(ru.kolodkin.shopcartreactivegrpc.entity.ShopCart.builder()
                        .productId(productId)
                        .userId(userId)
                        .quantity(1L)
                        .build())
                .doOnSuccess(success -> log.info("Товар добавлен в корзину пользователя userId: {}", success.getUserId()));
    }

    @Transactional
    @Override
    public Flux<ShopCartDTO> getAllShopCart(Long userId) {
        return shopCartListToDTOList(repository.findAllByUserId(userId))
                .doOnError(error -> log.error(error.getMessage()));
    }

    @Transactional
    @Override
    public Mono<ShopCart> updateProductQuantityInShopCart(Long userId, Long productId, Long quantity) {
        return repository.updateQuantityByUserIdAndProductId(userId, productId, quantity)
                .doOnError(error -> log.error(error.getMessage()))
                .doOnSuccess(success -> log.info("Информация о количестве товаров успешно обновлена"));
    }

    @Transactional
    @Override
    public Mono<ShopCartDTO> getShopCartProduct(Long userId, Long productId) {
        return shopCartToDTO(repository.findByUserIdAndProductId(userId, productId))
                .doOnError(error -> log.error(error.getMessage()))
                .doOnNext(next -> log.info("Получен товар из корзины пользователя userId: {}", next.getUserId()));
    }

    @Override
    public Mono<Void> deleteProductFromShopCart(Long userId, Long productId) {
        return repository.deleteByUserIdAndProductId(userId, productId)
                .doOnError(error -> log.error(error.getMessage()))
                .doOnSuccess(success -> log.info("Товар: {} пользователя: {} удалён", productId, userId));

    }

    @Override
    public Mono<Void> deleteProductsFromShopCart(Long userId) {
        return repository.deleteAllByUserId(userId)
                .doOnError(error -> log.error(error.getMessage()))
                .doOnSuccess(success -> log.info("Удалены все товары пользователя: {}", userId));
    }

    private Flux<ShopCartDTO> shopCartListToDTOList(final Flux<ShopCart> shopCartFlux) {
        return client.getProductsByIds(shopCartFlux
                        .map(ShopCart::getProductId))
                .collectMap(ProductInfo::getId, productInfo -> productInfo)
                .flatMapMany(productMap ->
                        shopCartFlux.flatMap(shopCart -> {
                            ProductInfo productInfo = productMap.get(shopCart.getProductId());

                            if (productInfo != null) {
                                return Mono.just(ShopCartDTO.builder()
                                        .product(productInfo)
                                        .quantity(shopCart.getQuantity())
                                        .userId(shopCart.getUserId())
                                        .build());
                            } else {
                                return Mono.empty();
                            }
                        })
                );
    }

    private Mono<ShopCartDTO> shopCartToDTO(final Mono<ShopCart> shopCartMono) {
        return shopCartMono.flatMap(shopCart ->
                client.getProductById(shopCart.getProductId())
                        .flatMap(productInfo ->
                                Mono.just(ShopCartDTO.builder()
                                        .product(productInfo)
                                        .userId(shopCart.getUserId())
                                        .quantity(shopCart.getQuantity())
                                        .build())
                        )
        );
    }
}
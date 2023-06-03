package ru.kolodkin.shopcartreactivegrpc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.shopcartreactivegrpc.bean.ProductInfo;
import ru.kolodkin.shopcartreactivegrpc.bean.ShopCartDTO;
import ru.kolodkin.shopcartreactivegrpc.bean.ShopCartSimpleDTO;
import ru.kolodkin.shopcartreactivegrpc.entity.ShopCart;
import ru.kolodkin.shopcartreactivegrpc.repository.ShopCartRepository;

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
                .build());
    }

    @Transactional
    @Override
    public Flux<ShopCartDTO> getAllShopCart(Long userId) {
        return shopCartListToDTOList(repository.findAllByUserId(userId));
    }

    @Transactional
    @Override
    public Mono<ShopCart> updateProductQuantityInShopCart(Long userId, Long productId, Long quantity) {
        return repository.updateQuantityByUserIdAndProductId(userId, productId, quantity);
    }

    @Transactional
    @Override
    public Mono<ShopCartDTO> getShopCartProduct(Long userId, Long productId) {
        return shopCartToDTO(repository.findByUserIdAndProductId(userId, productId));
    }

    @Transactional
    @Override
    public Mono<Void> deleteProductFromShopCart(Long userId, Long productId) {
        return repository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    @Override
    public Mono<Void> deleteProductsFromShopCart(Long userId) {
        return repository.deleteAllByUserId(userId);
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
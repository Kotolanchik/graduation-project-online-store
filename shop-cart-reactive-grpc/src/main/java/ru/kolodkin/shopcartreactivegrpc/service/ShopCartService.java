package ru.kolodkin.shopcartreactivegrpc.service;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.grpc.shop.cart.service.*;
import ru.kolodkin.shopcartreactivegrpc.bean.ShopCartDTO;
import ru.kolodkin.shopcartreactivegrpc.entity.ShopCart;

public interface ShopCartService {
    Mono<ShopCart> addProductInShopCart(Long userId, Long productId);

    Flux<ShopCartDTO> getAllShopCart(Long userId);

    Mono<ShopCart> updateProductQuantityInShopCart(Long userId, Long productId, Long quantity);

    Mono<Mono<ShopCartDTO>> getShopCartProduct(Long userId, Long productId);

    Mono<Void> deleteProductFromShopCart(Long userId, Long productId);

    Mono<Void> deleteProductsFromShopCart(Long userId);
}


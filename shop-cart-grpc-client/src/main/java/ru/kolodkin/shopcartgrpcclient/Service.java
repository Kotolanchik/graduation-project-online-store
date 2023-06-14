package ru.kolodkin.shopcartgrpcclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import reactor.core.publisher.Mono;
import ru.grpc.shop.cart.service.*;
//import ru.grpc.shop.cart.service.*;

@org.springframework.stereotype.Service
public class Service {
    private final ReactorShopCartServiceGrpc.ReactorShopCartServiceStub stub;

    public Service() {
        this.stub = ReactorShopCartServiceGrpc.newReactorStub(ManagedChannelBuilder.forTarget("localhost:9090")
                .usePlaintext()
                .build());
    }

    public void addProductInShopCart(final Long userId, final Long productId) {
       AddProductInShopCartRequest request = AddProductInShopCartRequest.newBuilder()
                .setProductId(productId)
                .setUserId(userId)
                .build();

        stub.addProductInShopCart(Mono.just(request))
                .subscribe()
                .dispose();
    }

    public void getAllShopCart(final Long userId) {
        GetUserShopCartRequest request = GetUserShopCartRequest.newBuilder()
                .setUserId(userId)
                .build();

        stub.getAllShopCart(Mono.just(request))
                .subscribe()
                .dispose();
    }

    public void updateProductQuantityInShopCart(final Long userId, final Long productId, final Long quantity) {
        UpdateProductQuantityInShopCartRequest request = UpdateProductQuantityInShopCartRequest.newBuilder()
                .setShopCart(ShopCart.newBuilder()
                        .setQuantity(quantity)
                        .setProductId(productId)
                        .setUserId(userId))
                .build();

        stub.updateProductQuantityInShopCart(Mono.just(request))
                .subscribe()
                .dispose();
    }

    public void getShopCartProduct(final Long userId, final Long productId) {
        GetShopCartItemRequest request = GetShopCartItemRequest.newBuilder()
                .setUserId(userId)
                .setProductId(productId)
                .build();

        stub.getShopCartProduct(Mono.just(request))
                .subscribe()
                .dispose();
    }

    public void deleteProductFromShopCart(final Long userId, final Long productId) {
        DeleteProductFromShopCartRequest request = DeleteProductFromShopCartRequest.newBuilder()
                .setProductId(productId)
                .setUserId(userId)
                .build();

        stub.deleteProductFromShopCart(Mono.just(request))
                .subscribe()
                .dispose();
    }

    public void deleteProductsFromShopCart(final Long userId) {
        DeleteProductsFromShopCartRequest request = DeleteProductsFromShopCartRequest.newBuilder()
                .setUserId(userId)
                .build();

        stub.deleteProductsFromShopCart(Mono.just(request))
                .subscribe()
                .dispose();
    }
}

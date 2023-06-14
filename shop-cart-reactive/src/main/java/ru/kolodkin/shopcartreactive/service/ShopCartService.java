package ru.kolodkin.shopcartreactive.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.shopcartreactive.bean.ProductInfo;
import ru.kolodkin.shopcartreactive.bean.ShopCartDTO;
import ru.kolodkin.shopcartreactive.bean.ShopCartSimpleDTO;
import ru.kolodkin.shopcartreactive.entity.ShopCart;
import ru.kolodkin.shopcartreactive.repository.ShopCartRepository;

import java.util.Map;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ShopCartService {
    ShopCartRepository repository;
    Client client;

    public Mono<Void> deleteAll(){
        repository.deleteAll();
        return Mono.empty();
    }

    public Flux<ShopCartDTO> getShopCartByUserId(final Long userId) {
        return shopCartListToDTOList(repository.findAllByUserId(userId));
    }

    public Mono<Mono<ShopCartDTO>> getShopCartByUserIdAndProductId(final Long userId, final Long productId) {
        return shopCartToDTO(repository.findByUserIdAndProductId(userId, productId));
    }

    @Transactional
    public Mono<ShopCart> addProductInCart(final Long userId, final Long productId){
        return repository.save(ShopCart.builder()
                .productId(productId)
                .userId(userId)
                .quantity(1L).build());
    }

    @Transactional
    public Mono<ShopCartSimpleDTO> setShopCart(final Long userId, final Long productId, final Long quantity) {
       /* return repository.findByUserIdAndProductId(userId, productId)
                .flatMap(shopCart -> {

                    return repository.updateQuantity(userId, productId, quantity);
                })
                .defaultIfEmpty(ShopCart.builder()
                        .userId(userId)
                        .productId(productId)
                        .quantity(quantity)
                        .build()
                ).flatMap(repository::save)
                .map(ShopCart::getProductId);*/

        return repository.updateQuantityByUserIdAndProductId(userId, productId,quantity)
                .map(shopCart -> ShopCartSimpleDTO.builder()
                        .productId(shopCart.getProductId())
                        .userId(shopCart.getUserId())
                        .quantity(shopCart.getQuantity())
                        .build());

      /*  return repository.findByUserIdAndProductId(userId, productId)
                .flatMap(shopCart -> {
                    shopCart.setQuantity(quantity);
                    return repository.save(shopCart);
                });*/
    }

    @Transactional
    public Mono<Void> deleteProductFromCart(final Long userId, final Long productId) {
        return repository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    public Mono<Void> deleteProductsFromCart(final Long userId) {
        return repository.deleteAllByUserId(userId);
    }

    private Flux<ShopCartDTO> shopCartListToDTOList(Flux<ShopCart> shopCartFlux) {
        return client.getProductsByIds(shopCartFlux.map(ShopCart::getProductId))
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
                );
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

//    private Flux<ShopCartDTO> shopCartListToDTOList(final Flux<ShopCart> shopCartFlux) {
//        return client.getProductsByIds(shopCartFlux
//                        .map(ShopCart::getProductId))
//                .collectMap(ProductInfo::getId, productInfo -> productInfo)
//                .flatMapMany(productMap ->
//                        shopCartFlux.flatMap(shopCart -> {
//                            ProductInfo productInfo = productMap.get(shopCart.getProductId());
//
//                            if (productInfo != null) {
//                                return Mono.just(ShopCartDTO.builder()
//                                        .product(productInfo)
//                                        .quantity(shopCart.getQuantity())
//                                        .userId(shopCart.getUserId())
//                                        .build());
//                            } else {
//                                return Mono.empty();
//                            }
//                        })
//                );
//    }
//
//    private Mono<ShopCartDTO> shopCartToDTO(final Mono<ShopCart> shopCartMono) {
//        return shopCartMono.flatMap(shopCart ->
//                client.getProductById(shopCart.getProductId())
//                        .flatMap(productInfo ->
//                                Mono.just(ShopCartDTO.builder()
//                                        .product(productInfo)
//                                        .userId(shopCart.getUserId())
//                                        .quantity(shopCart.getQuantity())
//                                        .build())
//                        )
//        );
//    }
}

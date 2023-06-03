package ru.kolodkin.shopcartreactivegrpc.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.shopcartreactivegrpc.entity.CompositeShopCart;
import ru.kolodkin.shopcartreactivegrpc.entity.ShopCart;

@Repository
public interface ShopCartRepository extends R2dbcRepository<ShopCart, CompositeShopCart> {

    Mono<ShopCart> findByUserIdAndProductId(Long userId, Long productId);

    Flux<ShopCart> findAllByUserId(Long userId);

    Mono<Void> deleteByUserIdAndProductId(Long userId, Long productId);

    Mono<Void> deleteAllByUserId(Long userId);

    @Query("""
            UPDATE public.shop_cart 
            SET quantity = :quantity 
            WHERE product_id = :productId AND user_id = :userId
            """)
    Mono<ShopCart> updateQuantityByUserIdAndProductId(Long userId, Long productId, Long quantity);
}

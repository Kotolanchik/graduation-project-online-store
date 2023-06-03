package ru.kolodkin.shopcartreactive.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.shopcartreactive.bean.ShopCartDTO;
import ru.kolodkin.shopcartreactive.bean.ShopCartSimpleDTO;
import ru.kolodkin.shopcartreactive.entity.ShopCart;
import ru.kolodkin.shopcartreactive.service.ShopCartService;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/shop-cart")
@Tags(@Tag(name = "Shop-cart", description = "Shop-cart REST Controller"))
public class ShopCartController {
    ShopCartService service;

    @GetMapping("/{id}")
    public Mono<ShopCartDTO> getShopCartProduct(@PathVariable("id") Long userId,
                                                @RequestParam("productId") final Long productId) {
        return service.getShopCartByUserIdAndProductId(userId, productId);
    }

    @GetMapping("/{userId}/all")
    public Flux<ShopCartDTO> getAllShopCart(@PathVariable final Long userId) {
        return service.getShopCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public Mono<ShopCart> addProductInShopCart(@PathVariable("userId") final Long userId,
                                           @RequestParam("productId") final Long productId) {
        return service.addProductInCart(userId, productId);
    }

    @PutMapping("/{userId}/update")
    public Mono<ShopCartSimpleDTO> updateProductQuantityInShopCart(@PathVariable("userId") final Long userId,
                                                        @RequestParam("productId") final Long productId,
                                                        @RequestParam("quantity") final Long quantity) {
        return service.setShopCart(userId, productId, quantity);
    }

    @DeleteMapping("/{userId}/delete")
    public Mono<Void> deleteProductFromShopCart(@PathVariable("userId") final Long userId,
                                                @RequestParam("productId") final Long productId) {
        return service.deleteProductFromCart(userId, productId);
    }

    @DeleteMapping("/{userId}/deleteAll")
    public Mono<Void> deleteProductsFromShopCart(@PathVariable("userId") final Long userId) {
        return service.deleteProductsFromCart(userId);
    }

}

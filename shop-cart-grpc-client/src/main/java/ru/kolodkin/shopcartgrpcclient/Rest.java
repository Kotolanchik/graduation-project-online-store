package ru.kolodkin.shopcartgrpcclient;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shop-cart")
public class Rest {
    private final Service service;

    public Rest(Service service) {
        this.service = service;
    }

    @RequestMapping("/{id}/{productId}")
    public void getShopCartProduct(@PathVariable("id") Long userId, @PathVariable("productId") Long productId){
        service.getShopCartProduct(userId, productId);
    }

    @RequestMapping("/{userId}/all")
    public void getAll(@PathVariable("userId") Long userId){
        service.getAllShopCart(userId);
    }


    @RequestMapping("/{userId}/add")
    public void addProductInShopCart(@PathVariable("userId") Long userId, @RequestParam("productId") Long productId){
        service.addProductInShopCart(userId, productId);
    }

    @RequestMapping("/{userId}/update")
    public void updateProduct(@PathVariable("userId") Long userId,
                              @RequestParam("productId") Long productId,
                              @RequestParam("quantity") Long quantity) {
        service.updateProductQuantityInShopCart(userId, productId, quantity);
    }

    @RequestMapping("/{userId}/delete")
    public void deleteProduct(@PathVariable("userId") Long userId,
                              @RequestParam("productId") Long productId){
        service.deleteProductFromShopCart(userId, productId);
    }

    @RequestMapping("/{userId}/deleteAll")
    public void deleteAll(@PathVariable("userId") Long userId) {
        service.deleteProductsFromShopCart(userId);
    }
}

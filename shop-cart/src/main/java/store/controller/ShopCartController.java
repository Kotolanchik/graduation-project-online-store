package store.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.bean.ShopCartListResponse;
import store.bean.ShopCartResponse;
import store.service.ShopCartService;

import static lombok.AccessLevel.PRIVATE;

@RestController
@ResponseBody
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/shop-cart")
@RequiredArgsConstructor
public class ShopCartController {
    ShopCartService service;

    @GetMapping("/{userId}/all")
    public ResponseEntity<ShopCartListResponse> getAllShopCart(@PathVariable final Long userId) {
        return ResponseEntity.ok(service.getShopCartByUserId(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ShopCartResponse> getShopCart(@PathVariable final Long userId, @RequestParam final Long productId) {
        return ResponseEntity.ok(service.getShopCartByUserIdAndProductId(userId, productId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> addProductInShopCart(@PathVariable final Long userId, @RequestParam final Long productId) {
        service.setUserShopCart(userId, productId, 1L);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all/delete")
    public ResponseEntity<?> deleteAll(){
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/{userId}")
    public ResponseEntity<?> setProductQuantity(@PathVariable final Long userId, @RequestParam final Long productId, @RequestParam final Long quantity) {
        service.setUserShopCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteProductInShopCart(@PathVariable final Long userId, @RequestParam final Long productId) {
        service.deleteUserShopCart(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll/{userId}")
    public ResponseEntity<?> deleteAllProductInShopCart(@PathVariable final Long userId) {
        service.deleteUserShopCartList(userId);
        return ResponseEntity.ok().build();
    }
}

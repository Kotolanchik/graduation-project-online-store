package store.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.bean.ProductInfo;
import store.bean.ShopCartDTO;
import store.bean.ShopCartListResponse;
import store.bean.ShopCartResponse;
import store.entity.ShopCart;
import store.repository.ShopCartRepository;


import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ShopCartService {
    ShopCartRepository repository;
    CatalogClient catalogClient;

    public ShopCartListResponse getShopCartByUserId(final Long userId) {
        return ShopCartListResponse.builder()
                .shopCartDTOList(shopCartListToDTOList(repository.findAllByUserId(userId)
                        .orElse(List.of())))
                .build();
    }

    public ShopCartResponse getShopCartByUserIdAndProductId(final Long userId, final long productId) {
        return ShopCartResponse.builder()
                .shopCartDTO(shopCartToDTO(repository.findByUserIdAndProductId(userId, productId)
                        .orElse(ShopCart.builder()
                                .productId(productId)
                                .userId(userId)
                                .quantity(0L)
                                .build())))
                .build();
    }

    @Transactional
    public void setUserShopCart(final Long userId, final Long productId, final Long quantity) {
        ShopCart shopCartItem = repository.findByUserIdAndProductId(userId, productId)
                .map(item -> {
                    item.setQuantity(quantity);
                    return item;
                })
                .orElseGet(() -> ShopCart.builder()
                        .userId(userId)
                        .productId(productId)
                        .quantity(quantity)
                        .build());

        repository.save(shopCartItem);
    }

    @Transactional
    public void deleteUserShopCartList(final Long userId) {
        repository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteUserShopCart(final Long userId, final Long productId) {
        repository.deleteByUserIdAndProductId(userId, productId);
    }

    private List<ShopCartDTO> shopCartListToDTOList(final List<ShopCart> shopCartList) {
        val productIds = shopCartList.stream()
                .map(ShopCart::getProductId)
                .toList();

        return shopCartList.stream()
                .flatMap(shopCart -> catalogClient.getProductsByIds(productIds)
                        .stream()
                        .filter(info -> info.getId()
                                .equals(shopCart.getProductId()))
                        .map(productInfo -> ShopCartDTO.builder()
                                .userId(shopCart.getUserId())
                                .quantity(shopCart.getQuantity())
                                .product(productInfo)
                                .build()))
                .toList();
    }

    private ShopCartDTO shopCartToDTO(final ShopCart shopCart) {
        val product = catalogClient.getProductById(shopCart.getProductId());

        if (product == null) {
            throw new RuntimeException();
        }

        return ShopCartDTO.builder()
                .product(product)
                .quantity(shopCart.getQuantity())
                .userId(shopCart.getUserId())
                .build();
    }
}

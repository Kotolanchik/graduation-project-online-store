package ru.kolodkin.shopcartreactivegrpc.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "shop_cart")
public class ShopCart {
    @Column("user_id")
    Long userId;
    @Column("product_id")
    Long productId;
    @Column("quantity")

    Long quantity;
}
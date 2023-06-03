package ru.kolodkin.shopcartreactivegrpc.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ShopCartSimpleDTO {
    Long userId;
    Long productId;
    Long quantity;
}

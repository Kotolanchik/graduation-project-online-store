package ru.kolodkin.shopcartreactivegrpc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompositeShopCart implements Serializable {
    Long userId;
    Long productId;
}

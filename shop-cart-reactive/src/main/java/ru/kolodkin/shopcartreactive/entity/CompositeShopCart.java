package ru.kolodkin.shopcartreactive.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class CompositeShopCart implements Serializable {
    Long userId;
    Long productId;
}

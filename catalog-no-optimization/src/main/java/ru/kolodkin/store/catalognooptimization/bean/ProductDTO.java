package ru.kolodkin.store.catalognooptimization.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kolodkin.store.catalognooptimization.domain.Attribute;
import ru.kolodkin.store.catalognooptimization.domain.Image;
import ru.kolodkin.store.catalognooptimization.domain.Product;


import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ProductDTO {
    Long id;
    String name;
    String description;
    BigDecimal price;
    Long amount;
    CategoryDTO category;
    Product.ProductState state;
    List<Image> images;
    List<Attribute> attributes;
}

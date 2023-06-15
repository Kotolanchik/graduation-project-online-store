package ru.store.catalog.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.store.catalog.domain.Attribute;
import ru.store.catalog.domain.Image;
import ru.store.catalog.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

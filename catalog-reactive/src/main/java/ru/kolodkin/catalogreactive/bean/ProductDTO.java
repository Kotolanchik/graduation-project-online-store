package ru.kolodkin.catalogreactive.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kolodkin.catalogreactive.entity.Attribute;
import ru.kolodkin.catalogreactive.entity.Category;
import ru.kolodkin.catalogreactive.entity.Image;

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
    ProductState state;
    List<Image> images;
    List<Attribute> attributes;
    CategoryDTO category;

    public enum ProductState {
        AVAILABLE,
        NOT_AVAILABLE
    }
}

package store.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ProductInfo {
    Long id;
    String name;
    String description;
    BigDecimal price;
    Long amount;
    CategoryInfo category;
    ProductState state;
    List<ImageInfo> images;
    List<AttributeInfo> attributes;

    public enum ProductState {
        AVAILABLE,
        NOT_AVAILABLE
    }
}


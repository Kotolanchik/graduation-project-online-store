package ru.store.catalog.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    Long id;
    String name;
    String description;
    BigDecimal price;
    Long amount;
    List<AttributeInfo> attributes;
    List<ImageInfo> images;
    private Pair<Long, String> category;
}

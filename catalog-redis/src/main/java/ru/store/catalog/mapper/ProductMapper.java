package ru.store.catalog.mapper;

import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.store.catalog.domain.Category;
import ru.store.catalog.domain.Product;
import ru.store.catalog.bean.ProductDTO;
import ru.store.catalog.bean.ProductInfo;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {
    Product toProduct(ProductDTO product);

    ProductDTO toProductDTO(Product product);

    @Mapping(source = "category", target = "category", qualifiedByName = "extractCategory")
    ProductInfo toProductInfo(Product product);

    @Named("extractCategory")
    default Pair<Long, String> extractCategory(Category category) {
        if (category == null) {
            throw new RuntimeException();
        }

        return Pair.of(category.getId(), category.getName());
    }
}

package ru.kolodkin.store.catalognooptimization.mapper;

import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.kolodkin.store.catalognooptimization.bean.ProductDTO;
import ru.kolodkin.store.catalognooptimization.bean.ProductInfo;
import ru.kolodkin.store.catalognooptimization.domain.Category;
import ru.kolodkin.store.catalognooptimization.domain.Product;


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

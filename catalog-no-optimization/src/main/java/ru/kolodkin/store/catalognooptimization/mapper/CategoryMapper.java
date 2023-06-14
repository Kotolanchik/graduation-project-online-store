package ru.kolodkin.store.catalognooptimization.mapper;

import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.kolodkin.store.catalognooptimization.bean.CategoryDTO;
import ru.kolodkin.store.catalognooptimization.bean.CategoryInfo;
import ru.kolodkin.store.catalognooptimization.domain.Category;


import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {
    @Mapping(source = "children", target = "children", qualifiedByName = "extractChildren")
    @Mapping(source = "parent", target = "parent", qualifiedByName = "extractParent")
    CategoryInfo categoryToCategoryInfo(Category category);

    CategoryDTO categoryToCategoryDTO(Category category);

    Category toCategory(CategoryDTO categoryDTO);

    @Named("extractChildren")
    default List<Pair<Long, String>> extractChildren(List<Category> categories) {
        if (categories == null) {
            return null;
        }

        return categories.stream()
                .map(category -> Pair.of(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    @Named("extractParent")
    default Pair<Long, String> extractParent(Category category) {
        if (category == null) {
            return null;
        }

        return Pair.of(category.getId(), category.getName());
    }
}

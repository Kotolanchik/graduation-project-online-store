package ru.kolodkin.catalogreactive.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.kolodkin.catalogreactive.bean.CategoryDTO;
import ru.kolodkin.catalogreactive.entity.Category;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
}

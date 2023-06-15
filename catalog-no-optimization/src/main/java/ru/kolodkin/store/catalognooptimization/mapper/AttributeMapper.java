package ru.kolodkin.store.catalognooptimization.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.kolodkin.store.catalognooptimization.bean.AttributeInfo;
import ru.kolodkin.store.catalognooptimization.domain.Attribute;

@Mapper(componentModel = "spring")
@Component
public interface AttributeMapper {
    Attribute toAttribute(AttributeInfo attributeInfo);
}

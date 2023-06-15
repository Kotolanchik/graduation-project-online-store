package ru.store.catalog.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.store.catalog.domain.Attribute;
import ru.store.catalog.bean.AttributeInfo;

@Mapper(componentModel = "spring")
@Component
public interface AttributeMapper {
    Attribute toAttribute(AttributeInfo attributeInfo);
}

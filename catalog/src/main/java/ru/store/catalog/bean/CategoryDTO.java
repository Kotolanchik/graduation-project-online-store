package ru.store.catalog.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.store.catalog.domain.Category;

import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CategoryDTO {
    Long id;
    String name;
    Category parent;
    List<Category> children;
}

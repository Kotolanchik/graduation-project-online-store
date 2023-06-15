package ru.kolodkin.store.catalognooptimization.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kolodkin.store.catalognooptimization.domain.Category;

import java.util.List;

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

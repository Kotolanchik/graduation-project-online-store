package ru.kolodkin.store.catalognooptimization.controller;

import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolodkin.store.catalognooptimization.bean.CategoryDTO;
import ru.kolodkin.store.catalognooptimization.bean.CategoryInfo;
import ru.kolodkin.store.catalognooptimization.service.CategoryService;


import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@RestController
@ResponseBody
@FieldDefaults(level = PRIVATE)
@RequestMapping("/category")
public class CategoryController {
    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryInfo>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/primary")
    public ResponseEntity<List<CategoryInfo>> getFirstLevelCategories() {
        return ResponseEntity.ok(categoryService.getFirstLevelCategories()
                .stream()
                .map(category -> CategoryInfo.builder()
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryInfo> getCategories(@PathVariable("id") final long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryInfo> saveCategory(@RequestBody final CategoryDTO category) {
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }
}

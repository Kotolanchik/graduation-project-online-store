package ru.store.catalog.controller;

import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.store.catalog.bean.CategoryDTO;
import ru.store.catalog.bean.CategoryInfo;
import ru.store.catalog.service.CategoryService;
import ru.store.catalog.service.ProductService;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@ResponseBody
@FieldDefaults(level = PRIVATE)
@RequestMapping("/category")
public class CategoryController {
    final CategoryService categoryService;

    public CategoryController( CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryInfo>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
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

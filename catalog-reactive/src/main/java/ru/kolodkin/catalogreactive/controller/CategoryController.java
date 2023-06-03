package ru.kolodkin.catalogreactive.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.catalogreactive.bean.CategoryDTO;
import ru.kolodkin.catalogreactive.service.CategoryService;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping(path = "/category")
public class CategoryController {
    CategoryService service;

    @GetMapping(path = "/all")
    public Flux<CategoryDTO> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<CategoryDTO>> getAllCategories(@PathVariable("id") final Long categoryId) {
        return service.getCategory(categoryId)
                .map(ResponseEntity::ok);
    }
}

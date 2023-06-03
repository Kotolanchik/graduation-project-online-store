package ru.kolodkin.catalogreactive.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.catalogreactive.bean.CategoryDTO;
import ru.kolodkin.catalogreactive.mapper.CategoryMapper;
import ru.kolodkin.catalogreactive.repo.CategoryRepository;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public Flux<CategoryDTO> getAllCategories(){
        return categoryRepository.findAll()
                .map(categoryMapper::categoryToCategoryDTO);
    }

    public Mono<CategoryDTO> getCategory(final Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::categoryToCategoryDTO);
    }
}

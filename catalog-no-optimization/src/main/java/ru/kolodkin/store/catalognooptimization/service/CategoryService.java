package ru.kolodkin.store.catalognooptimization.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolodkin.store.catalognooptimization.bean.CategoryDTO;
import ru.kolodkin.store.catalognooptimization.bean.CategoryInfo;
import ru.kolodkin.store.catalognooptimization.domain.Category;
import ru.kolodkin.store.catalognooptimization.mapper.CategoryMapper;
import ru.kolodkin.store.catalognooptimization.repository.CategoryRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryInfo saveCategory(final CategoryDTO category) {
        if (category == null) {
            throw new NullPointerException("Попытка создания категории без параметров.");
        }

        if (categoryRepository.findByName(category.getName()) != null) {
            throw new RuntimeException("Такая категория уже существует");
        }

        return categoryMapper.categoryToCategoryInfo(categoryRepository
                .save(categoryMapper.toCategory(category)));
    }


    @Transactional
    public CategoryInfo getCategory(final long categoryId) {
        if (categoryId < 1) {
            throw new IllegalArgumentException("Задано неправильное значение ID: " + categoryId);
        }

        return categoryRepository.findById(categoryId)
                .map(categoryMapper::categoryToCategoryInfo)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<CategoryInfo> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryInfo)
                .collect(Collectors.toList());
    }

    public List<Category> getFirstLevelCategories(){
        return categoryRepository.findFirstLevelCategories();
    }
}

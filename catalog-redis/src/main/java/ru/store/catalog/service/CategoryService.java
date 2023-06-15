package ru.store.catalog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.store.catalog.bean.CategoryDTO;
import ru.store.catalog.bean.CategoryInfo;
import ru.store.catalog.domain.Category;
import ru.store.catalog.mapper.CategoryMapper;
import ru.store.catalog.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@CacheConfig(cacheNames = {"Category"})
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

    @Cacheable(value = "primary")
    public List<CategoryInfo> getFirstLevelCategories(){
        return categoryRepository.findFirstLevelCategories()
                .stream()
                .map(categoryMapper::categoryToCategoryInfo)
                .toList();
    }
}

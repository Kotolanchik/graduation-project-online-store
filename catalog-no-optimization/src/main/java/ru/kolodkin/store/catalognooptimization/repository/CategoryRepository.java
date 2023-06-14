package ru.kolodkin.store.catalognooptimization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kolodkin.store.catalognooptimization.domain.Category;

import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("SELECT cat from Category cat WHERE cat.parent = null OR cat.parent.id = null")
    List<Category> findFirstLevelCategories();
}

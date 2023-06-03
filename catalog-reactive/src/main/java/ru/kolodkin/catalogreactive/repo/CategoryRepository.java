package ru.kolodkin.catalogreactive.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.catalogreactive.entity.Category;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, Long> {

    @Query("""
            SELECT t.category_id, t.name
            FROM public.category t
            WHERE category_id = :categoryId
            """)
    Mono<Category> findById(@Param("categoryId") Long categoryId);

    Flux<Category> findAll();
}

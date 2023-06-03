package ru.kolodkin.catalogreactive.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.kolodkin.catalogreactive.entity.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {


}

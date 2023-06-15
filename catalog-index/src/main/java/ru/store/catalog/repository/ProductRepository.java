package ru.store.catalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.store.catalog.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(final long id);

    List<Product> findAllByIdIn(List<Long> ids);

    @Query("SELECT prod from Product prod JOIN prod.category WHERE prod.category.id IS NOT NULL AND prod.category.id = :categoryId"
    )
    List<Product> findProductsByCategoryQuery(@Param("categoryId") final long categoryId);

    @Query("SELECT DISTINCT p FROM Product p JOIN FETCH p.attributes a WHERE p.id IN :productIds"
    )
    List<Product> findProductAttributes(@Param("productIds") final List<Long> productIds);

    @Query("SELECT DISTINCT p FROM Product p JOIN FETCH p.images i WHERE p.id IN :productIds"
    )
    List<Product> findProductImages(@Param("productIds") final List<Long> productIds);

    default List<Product> findProductsWithAttributesAndImagesByCategory(final long categoryId) {
        List<Product> products = findProductsByCategoryQuery(categoryId);
        if (products.isEmpty()) {
            throw new RuntimeException("В этой категории пусто");
        }

        List<Long> productIds = products.stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        for (Product product : products) {
            for (Product attribute : findProductAttributes(productIds)) {
                if (product.getId().equals(attribute.getId())) {
                    product.setAttributes(attribute.getAttributes());
                    break;
                }
            }
            for (Product image : findProductImages(productIds)) {
                if (product.getId().equals(image.getId())) {
                    product.setImages(image.getImages());
                    break;
                }
            }
        }

        return products;
    }


    //удалены жоины с аттрибутами и изображениями
    @Query(value =
            "SELECT DISTINCT prod from Product prod LEFT JOIN FETCH prod.category WHERE prod.category.id IS NOT NULL AND prod.category.id = :categoryId",
            countQuery = "SELECT count(prod) from Product prod LEFT JOIN prod.category WHERE prod.category.id IS NOT NULL AND prod.category.id = :categoryId")
    Page<Product> findProductsByCategoryId(@Param("categoryId") final long categoryId, Pageable pageable);


}

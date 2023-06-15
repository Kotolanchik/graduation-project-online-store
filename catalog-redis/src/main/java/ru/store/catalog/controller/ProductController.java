package ru.store.catalog.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.store.catalog.bean.ProductDTO;
import ru.store.catalog.bean.ProductInfo;
import ru.store.catalog.bean.pagination.PageRequest;
import ru.store.catalog.bean.pagination.PageSortRequest;
import ru.store.catalog.bean.pagination.SortRequest;
import ru.store.catalog.domain.Product;
import ru.store.catalog.service.ProductService;

import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/product")
@FieldDefaults(level = PRIVATE)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductInfo> getProduct(@PathVariable("id") final long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductInfo>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/productsFromCategory/{categoryId}")
    public ResponseEntity<List<ProductInfo>> getProductsByCategoryId(@PathVariable("categoryId") long categoryId) {
        return ResponseEntity.ok(productService.getProductsFromCategory(categoryId));
    }

    @GetMapping("/sortedProductsByCategory/{categoryId}")
    public ResponseEntity<List<ProductInfo>> getSortProductsByCategoryId(@PathVariable("categoryId") long categoryId,
                                                         @RequestBody final PageSortRequest pageSortRequest) {
        return ResponseEntity.ok(productService
                .getSortProductsByCategoryId(categoryId, pageSortRequest.getPageRequest(), pageSortRequest.getSortRequest()));
    }

    @GetMapping("/listByIds")
    public ResponseEntity<List<ProductInfo>> getProductsByIds(@RequestParam("productIds") List<Long> productIds) {
        return ResponseEntity.ok(productService.getProductsByIds(productIds));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductInfo> saveProduct(@RequestBody final ProductDTO product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") final long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}

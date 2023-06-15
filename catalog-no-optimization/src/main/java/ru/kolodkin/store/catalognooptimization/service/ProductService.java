package ru.kolodkin.store.catalognooptimization.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolodkin.store.catalognooptimization.bean.AttributeInfo;
import ru.kolodkin.store.catalognooptimization.bean.ProductDTO;
import ru.kolodkin.store.catalognooptimization.bean.ProductInfo;
import ru.kolodkin.store.catalognooptimization.bean.pagination.PageRequest;
import ru.kolodkin.store.catalognooptimization.bean.pagination.SortRequest;
import ru.kolodkin.store.catalognooptimization.mapper.AttributeMapper;
import ru.kolodkin.store.catalognooptimization.mapper.ProductMapper;
import ru.kolodkin.store.catalognooptimization.repository.ProductRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductService {
    ProductMapper productMapper;
    ProductRepository productRepository;
    AttributeMapper attributeMapper;

    @Transactional
    public List<ProductInfo> getProductsFromCategory(final Long categoryId) {
        if (categoryId < 1) {
            throw new IllegalArgumentException("Задано неправильное значение ID: " + categoryId);
        }

        return productRepository.findProductsWithAttributesAndImagesByCategory(categoryId).stream()
                .map(productMapper::toProductInfo)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductInfo> getSortProductsByCategoryId(@NotNull final Long categoryId,
                                                         @NotNull @Valid final PageRequest pageRequest,
                                                         @NotNull @Valid final SortRequest sortRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize(),
                StringUtils.equals(sortRequest.getSort(), "asc")
                        ? Sort.by(sortRequest.getSortBy()).ascending()
                        : Sort.by(sortRequest.getSortBy()).descending());

        return productRepository.findProductsByCategoryId(categoryId, pageable)
                .getContent()
                .stream()
                .map(productMapper::toProductInfo).collect(Collectors.toList());
    }

    public boolean addAttributeToTheProduct(final Long productId, final AttributeInfo attributeInfo) {
        val product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException());
        product.getAttributes()
                .add(attributeMapper.toAttribute(attributeInfo));
        return productRepository.save(product)
                .getId() > 0;
    }

    public boolean addAttributeListToTheProduct(final Long productId, final List<AttributeInfo> attributeInfoList) {
        val product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException());
        product.getAttributes()
                .addAll(attributeInfoList
                        .stream()
                        .map(attributeMapper::toAttribute)
                        .collect(Collectors.toList()));

        return productRepository.save(product)
                .getId() > 0;
    }


    public List<ProductInfo> getProductsByIds(final List<Long> productsIds) {
        if (productsIds.isEmpty()) {
            throw new NullPointerException("Список ID продуктов пуст.");
        }

        return productRepository.findAllByIdIn(productsIds)
                .stream()
                .map(productMapper::toProductInfo)
                .collect(Collectors.toList());
    }

    public List<ProductInfo> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductInfo)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductInfo getProduct(final long productId) {
        if (productId < 1) {
            throw new IllegalArgumentException("Задано неправильное значение ID: " + productId);
        }

        return productMapper.toProductInfo(productRepository
                .findById(productId));
    }

    public ProductInfo saveProduct(final ProductDTO product) {
        if (product == null) {
            throw new NullPointerException("Пустой объект");
        }

        return productMapper.toProductInfo(productRepository
                .save(productMapper.toProduct(product)));
    }

    public void deleteProduct(final long productId) {
        if (productId < 1) {
            throw new IllegalArgumentException("Задано неправильное значение ID: " + productId);
        }

        productRepository.deleteById(productId);
    }
}

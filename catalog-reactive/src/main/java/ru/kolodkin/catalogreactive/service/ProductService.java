package ru.kolodkin.catalogreactive.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.kolodkin.catalogreactive.bean.ProductDTO;
import ru.kolodkin.catalogreactive.mapper.CategoryMapper;
import ru.kolodkin.catalogreactive.repo.ProductRepository;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductService {
    ProductRepository repository;
    CategoryMapper categoryMapper;

    public Flux<ProductDTO> getAllProduct() {
       val f = repository.findAll()
               .map(el-> el.getCategory()
                       .getName());
       f.subscribe(System.out::println);
        return repository.findAll()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .amount(product.getAmount())
                        .attributes(product.getAttributes())
                        .category(categoryMapper.categoryToCategoryDTO(product.getCategory()))
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .images(product.getImages())
                        .name(product.getName())
                        .build());
    }
}

package ru.kolodkin.catalogreactive.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.kolodkin.catalogreactive.bean.ProductDTO;
import ru.kolodkin.catalogreactive.service.ProductService;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/product")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductController {

    ProductService service;

    @GetMapping("/all")
    public Flux<ProductDTO> getAllProduct() {
        return service.getAllProduct();
    }

}

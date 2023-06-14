package ru.kolodkin.store.catalognooptimization.service;

import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kolodkin.store.catalognooptimization.domain.Image;
import ru.kolodkin.store.catalognooptimization.mapper.ImageMapper;
import ru.kolodkin.store.catalognooptimization.repository.ImageRepository;
import ru.kolodkin.store.catalognooptimization.repository.ProductRepository;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ImageService {
    ImageRepository imageRepository;
    ProductRepository productRepository;
    ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.imageMapper = imageMapper;
    }

    public boolean addImageToTheProduct(final Long productId, final MultipartFile image) {
        val product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("не удалось найти товар"));

        try {
            product.getImages()
                    .add(Image.builder()
                            .fileContent(image.getBytes())
                            .name(image.getOriginalFilename())
                            .size(image.getSize())
                            .build());
        } catch (IOException e) {
            throw new RuntimeException("не удалось добавить изображение");
        }

        return productRepository.save(product)
                .getId() > 0;
    }

    public boolean addImageListToTheProduct(final Long productId, final List<MultipartFile> imageList) {
        val product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("не удалось найти товар"));
        product.getImages()
                .addAll(imageList
                        .stream()
                        .map(imageMapper::multipartFileToImage)
                        .collect(Collectors.toList()));
        return productRepository.save(product)
                .getId() > 0;
    }
}

package ru.kolodkin.store.catalognooptimization.controller;

import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kolodkin.store.catalognooptimization.service.ImageService;

import static lombok.AccessLevel.PRIVATE;

@RestController
@ResponseBody
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/image")
public class ImageController {
    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveImageForProduct(@RequestParam final Long productId, @RequestPart final MultipartFile image) {
        return ResponseEntity.ok(imageService.addImageToTheProduct(productId, image));
    }
}

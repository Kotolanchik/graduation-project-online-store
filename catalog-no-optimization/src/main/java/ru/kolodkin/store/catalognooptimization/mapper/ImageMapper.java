package ru.kolodkin.store.catalognooptimization.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.kolodkin.store.catalognooptimization.bean.ImageInfo;
import ru.kolodkin.store.catalognooptimization.domain.Image;

@Mapper(componentModel = "spring")
@Component
public interface ImageMapper {
    Image toImage(ImageInfo image);
    Image multipartFileToImage(MultipartFile image);
}

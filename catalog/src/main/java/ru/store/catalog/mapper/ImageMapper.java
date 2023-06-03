package ru.store.catalog.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.store.catalog.domain.Image;
import ru.store.catalog.bean.ImageInfo;

@Mapper(componentModel = "spring")
@Component
public interface ImageMapper {
    Image toImage(ImageInfo image);
    Image multipartFileToImage(MultipartFile image);
}

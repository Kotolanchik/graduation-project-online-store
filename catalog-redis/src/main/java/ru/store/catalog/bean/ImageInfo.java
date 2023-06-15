package ru.store.catalog.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ImageInfo implements Serializable {
    Long id;
    String name;
    String url;
}

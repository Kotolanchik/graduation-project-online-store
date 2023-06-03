package ru.kolodkin.shopcartreactivegrpc.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ImageInfo {
    Long id;
    String name;
    byte[] path;
    Long size;
}

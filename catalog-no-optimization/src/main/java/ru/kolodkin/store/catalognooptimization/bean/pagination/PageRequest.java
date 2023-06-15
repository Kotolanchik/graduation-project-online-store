package ru.kolodkin.store.catalognooptimization.bean.pagination;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageRequest {
    private int page = 0;
    private int size = 10;
}

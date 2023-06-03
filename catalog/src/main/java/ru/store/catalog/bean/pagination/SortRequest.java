package ru.store.catalog.bean.pagination;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SortRequest {
    private String sortBy = "id";
    private String sort = "asc";
}

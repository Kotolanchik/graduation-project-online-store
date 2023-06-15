package ru.store.catalog.bean.pagination;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageSortRequest {
    private SortRequest sortRequest;
    private PageRequest pageRequest;
}

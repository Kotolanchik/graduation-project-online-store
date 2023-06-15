package ru.kolodkin.store.catalognooptimization.bean.pagination;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageSortRequest {
    private SortRequest sortRequest;
    private PageRequest pageRequest;
}

package ru.kolodkin.store.catalognooptimization.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo {
    private Long id;
    private String name;
    private Pair<Long, String> parent;
    private List<Pair<Long, String>> children;
}

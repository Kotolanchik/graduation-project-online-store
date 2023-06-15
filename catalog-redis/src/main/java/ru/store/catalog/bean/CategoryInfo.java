package ru.store.catalog.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo implements Serializable {
    private Long id;
    private String name;
    private Pair<Long, String> parent;
    private List<Pair<Long, String>> children;
}

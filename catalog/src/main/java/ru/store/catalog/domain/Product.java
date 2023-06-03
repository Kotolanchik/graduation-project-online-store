package ru.store.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "price")
    BigDecimal price;
    @Column(name = "amount")
    Long amount;
    @Enumerated(EnumType.STRING)
    ProductState state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Image> images;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Attribute> attributes;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "product_category_fk"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Category category;

    public enum ProductState {
        AVAILABLE,
        NOT_AVAILABLE
    }
}

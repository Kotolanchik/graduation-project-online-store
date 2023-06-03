package ru.kolodkin.catalogreactive.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "product")
public class Product {
    @Id
    Long id;
    @Column("name")
    String name;
    @Column("description")
    String description;
    @Column("price")
    BigDecimal price;
    @Column("amount")
    Long amount;
    @Column("state")
    @Enumerated(EnumType.STRING)
    ProductState state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Image> images;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Attribute> attributes;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "product_category_fk"))
    Category category;

    public enum ProductState {
        AVAILABLE,
        NOT_AVAILABLE
    }
}
package store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "shop_cart")
@IdClass(ShopCartComposite.class)
public class ShopCart {
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    Long userId;

    @Id
    @Column(name = "product_id", unique = true, nullable = false)
    Long productId;

    @Column(nullable = false)
    Long quantity;
}

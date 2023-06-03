package store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.entity.ShopCart;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart, Long> {
    Optional<List<ShopCart>> findAllByUserId(Long userId);


    Optional<ShopCart> findByUserIdAndProductId(Long userId, Long productId);

    Optional<List<ShopCart>> findAllByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserId(Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);
}

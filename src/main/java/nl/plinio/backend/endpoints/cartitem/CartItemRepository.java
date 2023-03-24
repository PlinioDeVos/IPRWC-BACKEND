package nl.plinio.backend.endpoints.cartitem;

import nl.plinio.backend.endpoints.cartitem.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    @Override
    List<CartItem> findAll();

    List<CartItem> findAllByCartId(UUID cartId);

    CartItem findByProductId(UUID productId);

    void deleteAllByCartId(UUID id);
}

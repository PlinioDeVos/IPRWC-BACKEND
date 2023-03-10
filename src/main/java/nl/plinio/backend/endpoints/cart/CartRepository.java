package nl.plinio.backend.endpoints.cart;

import nl.plinio.backend.endpoints.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Override
    List<Cart> findAll();

    List<Cart> findAllByAccountId(UUID accountId);
}

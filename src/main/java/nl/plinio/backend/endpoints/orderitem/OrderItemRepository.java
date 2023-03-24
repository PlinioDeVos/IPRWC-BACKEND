package nl.plinio.backend.endpoints.orderitem;

import nl.plinio.backend.endpoints.orderitem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    @Override
    List<OrderItem> findAll();
}

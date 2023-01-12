package nl.plinio.backend.endpoints.order;

import nl.plinio.backend.endpoints.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Override
    List<Order> findAll();
}

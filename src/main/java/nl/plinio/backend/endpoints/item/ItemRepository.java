package nl.plinio.backend.endpoints.item;

import nl.plinio.backend.endpoints.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    @Override
    List<Item> findAll();

    List<Item> findAllByCartId(UUID cartId);
}

package nl.plinio.backend.endpoints.orderitem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.order.model.Order;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.model.BaseEntity;

@Entity
@Table(name = "order_items")
@Getter @Setter
public class OrderItem extends BaseEntity {
    @Positive
    private float unitPrice;

    @Positive
    private int quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;
}

package nl.plinio.backend.endpoints.orderitem.model;

import lombok.Value;

import java.util.UUID;

@Value
public class OrderItemDto {
    UUID id;
    float unit_price;
    int quantity;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.unit_price = orderItem.getUnitPrice();
        this.quantity = orderItem.getQuantity();
    }
}

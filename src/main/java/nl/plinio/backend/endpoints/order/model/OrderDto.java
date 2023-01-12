package nl.plinio.backend.endpoints.order.model;

import lombok.Value;
import nl.plinio.backend.endpoints.address.model.AddressDto;

import java.util.UUID;

@Value
public class OrderDto {
    UUID id;
    UUID cart_id;
    AddressDto address;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.cart_id = order.getCartId();
        this.address = new AddressDto(order.getAddress());
    }
}

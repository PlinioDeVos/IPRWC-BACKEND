package nl.plinio.backend.endpoints.cartitem.model;

import lombok.Value;
import nl.plinio.backend.endpoints.product.model.ProductDto;

import java.util.UUID;

@Value
public class CartItemDto {
    UUID id;
    int quantity;
    ProductDto product_reference;

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.product_reference = new ProductDto(cartItem.getProduct());
    }
}

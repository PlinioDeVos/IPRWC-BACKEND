package nl.plinio.backend.endpoints.item.model;

import lombok.Value;
import nl.plinio.backend.endpoints.product.model.ProductDto;

import java.util.UUID;

@Value
public class ItemDto {
    UUID id;
    float buy_price;
    ProductDto product_reference;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.buy_price = item.getBuyPrice();
        this.product_reference = new ProductDto(item.getProduct());
    }
}

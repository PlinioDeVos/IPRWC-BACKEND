package nl.plinio.backend.endpoints.product.model;

import lombok.Value;
import nl.plinio.backend.endpoints.image.model.ImageDto;

import java.util.UUID;

@Value
public class ProductDto {
    UUID id;
    String ean;
    String name;
    String description;
    float price;
    ImageDto image;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.ean = product.getEan();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.image = new ImageDto(product.getImage());
    }
}

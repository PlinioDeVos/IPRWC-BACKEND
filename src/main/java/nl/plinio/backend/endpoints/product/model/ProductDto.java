package nl.plinio.backend.endpoints.product.model;

import lombok.Value;

import java.util.UUID;

@Value
public class ProductDto {
    UUID id;
    String ean;
    String name;
    String description;
    String image;
    float price;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.ean = product.getEan();
        this.name = product.getName();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.price = product.getPrice();
    }
}

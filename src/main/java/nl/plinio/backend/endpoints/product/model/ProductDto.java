package nl.plinio.backend.endpoints.product.model;

import lombok.Value;

import java.util.UUID;

@Value
public class ProductDto {
    UUID id;
    String ean;
    String name;
    String description;
    float price;
    float salePrice;
    boolean visible;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.ean = product.getEan();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.salePrice = product.getSalePrice();
        this.visible = product.isVisible();
    }
}

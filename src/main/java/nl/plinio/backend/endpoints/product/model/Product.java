package nl.plinio.backend.endpoints.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.model.BaseEntity;

@Entity
@Table(name = "products")
@Getter @Setter
public class Product extends BaseEntity {
    private String ean;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @PositiveOrZero
    private float price;

    @PositiveOrZero
    private float salePrice;

    private boolean visible;
}

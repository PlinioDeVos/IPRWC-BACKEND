package nl.plinio.backend.endpoints.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.item.model.Item;
import nl.plinio.backend.model.BaseEntity;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Entity
@Table(name = "products")
@Getter @Setter
public class Product extends BaseEntity {
    private String ean;

    private String name;

    private String description;

    @URL
    private String image;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @PositiveOrZero
    private float price;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Item> items;
}

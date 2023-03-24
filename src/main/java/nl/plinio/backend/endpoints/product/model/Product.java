package nl.plinio.backend.endpoints.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.cartitem.model.CartItem;
import nl.plinio.backend.endpoints.image.model.Image;
import nl.plinio.backend.model.BaseEntity;

import java.util.Set;

@Entity
@Table(name = "products")
@Getter @Setter
public class Product extends BaseEntity {
    private String ean;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Positive(message = "Price must be greater than 0")
    private float price;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;
}

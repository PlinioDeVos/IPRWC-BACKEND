package nl.plinio.backend.endpoints.item.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.cart.model.Cart;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.model.BaseEntity;

@Entity
@Table(name = "items")
@Getter @Setter
public class Item extends BaseEntity {
    @PositiveOrZero
    private float buyPrice;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;
}

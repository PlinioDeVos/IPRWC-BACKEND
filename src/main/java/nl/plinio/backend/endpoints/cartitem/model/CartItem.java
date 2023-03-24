package nl.plinio.backend.endpoints.cartitem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.cart.model.Cart;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.model.BaseEntity;

@Entity
@Table(name = "cart_items")
@Getter @Setter
public class CartItem extends BaseEntity {
    @Positive
    private int quantity;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;
}

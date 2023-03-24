package nl.plinio.backend.endpoints.cart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.cartitem.model.CartItem;
import nl.plinio.backend.model.BaseEntity;

import java.util.Set;

@Entity
@Table(name = "carts")
@Getter @Setter
public class Cart extends BaseEntity {
    @OneToOne(mappedBy = "cart")
    private Account account;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;
}

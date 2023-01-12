package nl.plinio.backend.endpoints.cart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.item.model.Item;
import nl.plinio.backend.model.BaseEntity;

import java.util.Set;

@Entity
@Table(name = "carts")
@Getter @Setter
public class Cart extends BaseEntity {
    private boolean finalised;

    @ManyToOne
    private Account account;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Item> items;
}

package nl.plinio.backend.endpoints.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.cart.model.Cart;
import nl.plinio.backend.model.BaseEntity;

import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter @Setter
public class Account extends BaseEntity {
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "text")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Cart> carts;
}

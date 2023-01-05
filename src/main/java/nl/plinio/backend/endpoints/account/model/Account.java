package nl.plinio.backend.endpoints.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.model.BaseEntity;

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
}

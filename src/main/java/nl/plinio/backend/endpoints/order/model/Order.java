package nl.plinio.backend.endpoints.order.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.address.model.Address;
import nl.plinio.backend.model.BaseEntity;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order extends BaseEntity {
    private UUID cartId;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}

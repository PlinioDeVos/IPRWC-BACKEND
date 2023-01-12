package nl.plinio.backend.endpoints.address.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.order.model.Order;
import nl.plinio.backend.model.BaseEntity;

@Entity
@Table(name = "addresses")
@Getter @Setter
public class Address extends BaseEntity {
    private String country;

    private String province;

    private String street;

    private int streetNumber;

    private String postalCode;

    @OneToOne(mappedBy = "address")
    private Order order;
}

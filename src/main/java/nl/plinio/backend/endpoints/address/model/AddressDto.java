package nl.plinio.backend.endpoints.address.model;

import lombok.Value;

import java.util.UUID;

@Value
public class AddressDto {
    UUID id;
    String country;
    String province;
    String street;
    int street_number;
    String postal_code;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.country = address.getCountry();
        this.province = address.getProvince();
        this.street = address.getStreet();
        this.street_number = address.getStreetNumber();
        this.postal_code = address.getPostalCode();
    }
}

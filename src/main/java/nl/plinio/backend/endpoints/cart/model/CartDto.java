package nl.plinio.backend.endpoints.cart.model;

import lombok.Value;
import nl.plinio.backend.endpoints.account.model.AccountDto;

import java.util.UUID;

@Value
public class CartDto {
    UUID id;
    AccountDto account;
    boolean finalised;

    public CartDto(Cart cart) {
        this.id = cart.getId();
        this.account = new AccountDto(cart.getAccount());
        this.finalised = cart.isFinalised();
    }
}

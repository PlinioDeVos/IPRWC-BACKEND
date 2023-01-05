package nl.plinio.backend.endpoints.account.model;

import lombok.Value;

import java.util.UUID;

@Value
public class AccountDto {
    UUID id;
    String email;
    Role role;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.role = account.getRole();
    }
}

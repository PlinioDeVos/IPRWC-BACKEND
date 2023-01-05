package nl.plinio.backend.endpoints.auth.model;

import lombok.Value;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.account.model.AccountDto;

@Value
public class LoginDto {
    String jwt;
    AccountDto account;

    public LoginDto(String jwt, Account account) {
        this.jwt = jwt;
        this.account = new AccountDto(account);
    }
}

package nl.plinio.backend.helper;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.account.AccountService;
import nl.plinio.backend.endpoints.account.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelationHelper {
    private final AccountService accountService;

    public Account getAccount() {
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.findAccountByEmail(email);
    }
}

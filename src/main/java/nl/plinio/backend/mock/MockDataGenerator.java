package nl.plinio.backend.mock;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.account.AccountService;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.account.model.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockDataGenerator {
    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final AccountService accountService;

    private void createAdminAccount() {
        Account account = new Account();
        account.setEmail(adminEmail);
        account.setPassword(adminPassword);
        account.setRole(Role.ADMIN);
        accountService.createAccount(account);
    }

    @PostConstruct
    public void generate() {
        createAdminAccount();
    }
}

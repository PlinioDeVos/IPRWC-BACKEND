package nl.plinio.backend.helper;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.auth.cryptography.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHelper {
    @Value("${hash.pepper}")
    private String pepper;

    private final CustomPasswordEncoder passwordEncoder;

    public String appendPepper(String password) {
        return password + pepper;
    }

    public String encode(String password) {
        return passwordEncoder.encode(appendPepper(password));
    }

    public Account hashAccount(Account account) {
        String password = encode(account.getPassword());
        account.setPassword(password);

        return account;
    }

    public boolean matches(String password, String encodedPassword) {
        return passwordEncoder.matches(appendPepper(password), encodedPassword);
    }
}

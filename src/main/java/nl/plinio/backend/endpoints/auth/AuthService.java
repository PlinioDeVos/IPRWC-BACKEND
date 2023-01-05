package nl.plinio.backend.endpoints.auth;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.account.AccountService;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.auth.jwt.CookieGenerator;
import nl.plinio.backend.endpoints.auth.jwt.JwtGenerator;
import nl.plinio.backend.endpoints.auth.model.LoginDto;
import nl.plinio.backend.endpoints.auth.model.LoginRequest;
import nl.plinio.backend.exception.InvalidCredentialsException;
import nl.plinio.backend.helper.PasswordHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${jwt.lifetime-in-ms}")
    private Long lifetimeInMs;

    private final AccountService accountService;
    private final JwtGenerator jwtGenerator;
    private final CookieGenerator cookieGenerator;
    private final AuthenticationManager authenticationManager;
    private final PasswordHelper passwordHelper;

    public LoginDto login(LoginRequest loginRequest) {
        Account account = accountService.findAccountByEmail(loginRequest.getEmail());
        String password = passwordHelper.appendPepper(loginRequest.getPassword());

        if (!passwordHelper.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new InvalidCredentialsException();
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), password));
        String jwt = jwtGenerator.generate(authentication);

        return new LoginDto(jwt, account);
    }

    public HttpCookie createCookie(String jwt) {
        long cookieLifetimeInS = lifetimeInMs / 1000;
        return cookieGenerator.generate(jwt, cookieLifetimeInS);
    }

    public HttpCookie createCookie(String jwt, long cookieLifetimeInS) {
        return cookieGenerator.generate(jwt, cookieLifetimeInS);
    }
}
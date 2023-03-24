package nl.plinio.backend.endpoints.auth;

import com.nimbusds.jose.proc.BadJWSException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.account.AccountService;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.account.model.Role;
import nl.plinio.backend.endpoints.auth.jwt.CookieGenerator;
import nl.plinio.backend.endpoints.auth.jwt.JwtGenerator;
import nl.plinio.backend.endpoints.auth.model.LoginDto;
import nl.plinio.backend.endpoints.auth.model.LoginRequest;
import nl.plinio.backend.exception.EntityExistsException;
import nl.plinio.backend.exception.InvalidCredentialsException;
import nl.plinio.backend.exception.JwtExpiredException;
import nl.plinio.backend.helper.JwtHelper;
import nl.plinio.backend.helper.PasswordHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${jwt.lifetime-in-ms}")
    private Long lifetimeInMs;

    private final AccountService accountService;
    private final JwtGenerator jwtGenerator;
    private final JwtHelper jwtHelper;
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

    public LoginDto signup(LoginRequest signupRequest) {
        if (accountService.existsByEmail(signupRequest.getEmail())) {
            throw new EntityExistsException(
                    "An entity with the e-mail '" + signupRequest.getEmail() + "' already exists.");
        }

        Account account = new Account();
        account.setEmail(signupRequest.getEmail());
        account.setPassword(signupRequest.getPassword());
        account.setRole(Role.CUSTOMER);
        accountService.createAccount(account);

        return login(signupRequest);
    }

    public HttpCookie createCookie(String jwt) {
        long cookieLifetimeInS = lifetimeInMs / 1000;
        return cookieGenerator.generate(jwt, cookieLifetimeInS);
    }

    public HttpCookie createCookie(String jwt, long cookieLifetimeInS) {
        return cookieGenerator.generate(jwt, cookieLifetimeInS);
    }

    public void validateJwt(String jwt) {
        if (jwtHelper.isJwtExpired(jwt)) {
            throw new JwtExpiredException();
        }
    }
}
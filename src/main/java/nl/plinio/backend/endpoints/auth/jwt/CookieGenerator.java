package nl.plinio.backend.endpoints.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieGenerator {
    @Value("${jwt.cookie-name}")
    private String cookieName;

    @Value("${jwt.cookie-secure}")
    private boolean cookieSecure;

    @Value("${jwt.cookie-same-site}")
    private boolean cookieSameSite;

    public HttpCookie generate(String jwt, long cookieLifetimeInS) {
        return ResponseCookie.from(cookieName, jwt)
                .path("/")
                .httpOnly(true)
                .maxAge(cookieLifetimeInS)
                .secure(cookieSecure)
                .sameSite(cookieSameSite ? "Strict" : "Lax")
                .build();
    }
}

package nl.plinio.backend.helper;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtHelper {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.cookie-name}")
    private String cookieName;

    public JwtDecoder jwtDecoder() {
        byte[] bytes = secret.getBytes();
        SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length,"RSA");
        return NimbusJwtDecoder.withSecretKey(originalKey).build();
    }

    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.secret.getBytes()));
    }

    public Jwt decode(String jwt) {
        JwtDecoder jwtDecoder = jwtDecoder();
        return jwtDecoder.decode(jwt);
    }

    public boolean isJwtExpired(String jwt) {
        Jwt decodedJwt = decode(jwt);
        Instant expiresAt = decodedJwt.getExpiresAt();

        if (expiresAt != null) {
            return Instant.now().isAfter(expiresAt);
        }

        return true;
    }

    public boolean isValidJwt(String jwt) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            JWSVerifier verifier = new MACVerifier(secret);
            return signedJWT.verify(verifier);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return false;
    }

    public String retrieveJwt(HttpServletRequest request) {
        String jwt = getJwtFromCookie(request);
        return (jwt != null) ? jwt : getJwtFromRequest(request);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
                ? bearerToken.substring(7) : null;
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}

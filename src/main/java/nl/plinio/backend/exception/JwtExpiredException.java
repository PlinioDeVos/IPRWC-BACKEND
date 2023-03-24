package nl.plinio.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class JwtExpiredException extends RuntimeException {
    public JwtExpiredException() {
        super("The JWT has expired.");
    }
}

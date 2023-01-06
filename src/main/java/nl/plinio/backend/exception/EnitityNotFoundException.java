package nl.plinio.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnitityNotFoundException extends RuntimeException {
    public <T> EnitityNotFoundException(Class<T> entity) {
        super("Entity of type '" + entity.getSimpleName() + "' not found.");
    }
}

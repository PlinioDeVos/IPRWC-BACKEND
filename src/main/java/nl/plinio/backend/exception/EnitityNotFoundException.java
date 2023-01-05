package nl.plinio.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnitityNotFoundException extends RuntimeException {
    public <T> EnitityNotFoundException(Class<T> entity) {
        super("Entity of type '" + entity.getSimpleName() + "' not found.");
        log.error("Entity of type '" + entity.getSimpleName() + "' not found.");
    }
}

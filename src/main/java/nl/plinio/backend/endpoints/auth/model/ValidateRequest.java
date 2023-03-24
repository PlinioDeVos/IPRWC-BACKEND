package nl.plinio.backend.endpoints.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidateRequest {
    private String jwt;
}

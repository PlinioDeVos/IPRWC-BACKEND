package nl.plinio.backend.endpoints.auth.model;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequest {
    @Email
    private String email;

    private String password;
}

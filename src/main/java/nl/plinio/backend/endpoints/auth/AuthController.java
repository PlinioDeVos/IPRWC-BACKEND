package nl.plinio.backend.endpoints.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.auth.model.LoginDto;
import nl.plinio.backend.endpoints.auth.model.LoginRequest;
import nl.plinio.backend.endpoints.auth.model.ValidateRequest;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<LoginDto> signup(@Valid @RequestBody LoginRequest signupRequest) {
        LoginDto loginDto = authService.signup(signupRequest);
        HttpCookie cookie = authService.createCookie(loginDto.getJwt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginDto loginDto = authService.login(loginRequest);
        HttpCookie cookie = authService.createCookie(loginDto.getJwt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginDto);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> logout() {
        HttpCookie cookie = authService.createCookie("", 0);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(null);
    }

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public void validate(@Valid @RequestBody ValidateRequest validateRequest) {
        authService.validateJwt(validateRequest.getJwt());
    }
}

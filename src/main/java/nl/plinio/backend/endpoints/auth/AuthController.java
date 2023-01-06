package nl.plinio.backend.endpoints.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.account.model.AccountDto;
import nl.plinio.backend.endpoints.auth.model.LoginDto;
import nl.plinio.backend.endpoints.auth.model.LoginRequest;
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
    public ResponseEntity<AccountDto> signup(@Valid @RequestBody LoginRequest signupRequest) {
        LoginDto loginDto = authService.signup(signupRequest);
        HttpCookie cookie = authService.createCookie(loginDto.getJwt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginDto.getAccount());
    }

    @PostMapping("/login")
    public ResponseEntity<AccountDto> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginDto loginDto = authService.login(loginRequest);
        HttpCookie cookie = authService.createCookie(loginDto.getJwt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginDto.getAccount());
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> logout() {
        HttpCookie cookie = authService.createCookie("", 0);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(null);
    }
}

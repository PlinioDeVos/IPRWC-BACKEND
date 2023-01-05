package nl.plinio.backend.endpoints.auth;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.account.model.AccountDto;
import nl.plinio.backend.endpoints.auth.model.LoginDto;
import nl.plinio.backend.endpoints.auth.model.LoginRequest;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AccountDto> login(@RequestBody LoginRequest loginRequest) {
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

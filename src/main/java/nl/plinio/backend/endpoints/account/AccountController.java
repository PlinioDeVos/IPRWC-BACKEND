package nl.plinio.backend.endpoints.account;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.account.model.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@PreAuthorize("hasAuthority('admin')")
@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AccountDto> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return accountService.getAllAccounts(page, size, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(@PathVariable UUID id) {
        return new AccountDto(accountService.findAccount(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto updateAccount(@PathVariable UUID id, @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
    }
}

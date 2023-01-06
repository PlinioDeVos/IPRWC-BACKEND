package nl.plinio.backend.endpoints.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.account.model.AccountDto;
import nl.plinio.backend.exception.EmailNotFoundException;
import nl.plinio.backend.exception.EnitityNotFoundException;
import nl.plinio.backend.helper.PasswordHelper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordHelper passwordHelper;

    public Account findAccount(UUID id) {
        return accountRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(Account.class));
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Page<AccountDto> getAllAccounts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<AccountDto> accountResponseList = new ArrayList<>();
        Page<Account> existingAccounts = accountRepository.findAll(pageable);
        existingAccounts.forEach(account -> accountResponseList.add(new AccountDto(account)));
        return new PageImpl<>(accountResponseList, pageable, existingAccounts.getTotalElements());
    }

    public AccountDto createAccount(Account account) {
        Account hashedAccount = passwordHelper.hashAccount(account);
        return new AccountDto(accountRepository.save(hashedAccount));
    }

    public AccountDto updateAccount(UUID id, Account account) {
        Account existingAccount = findAccount(id);
        existingAccount.setEmail(account.getEmail());
        return new AccountDto(accountRepository.save(existingAccount));
    }

    public void deleteAccount(UUID id) {
        try {
            accountRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(Account.class);
        }
    }
}

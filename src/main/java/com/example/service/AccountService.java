package com.example.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account registerAccount(Account account) throws Exception {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid username or password is too short.");
        }
        if (accountRepository.findAccountByUsername(account.getUsername()).isPresent()) {
            throw new Exception("Username already exists.");
        }
        return accountRepository.save(account);
    }

    public Account loginAccount(String username, String password) throws Exception {
        Optional<Account> account = accountRepository.findAccountByUsername(username);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return account.get();
        } else {
            throw new Exception("Invalid password or username.");
        }
    }
}

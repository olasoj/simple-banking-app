package com.bank.operation.bank.repository;

import com.bank.operation.bank.model.entity.Account;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Component
public class AccountRepository {
    private static final Map<String, Account> accounts = new TreeMap<>();

    public void save(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public void update(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    public Map<String, Account> findAll() {
        return accounts;
    }

    public boolean accountNameExists(String accountName) {
        for (Map.Entry<String, Account> account : accounts.entrySet())
            if (accounts.get(account.getKey()).getAccountName().equals(accountName))
                return true;
        return false;
    }

}

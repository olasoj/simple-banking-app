package com.bank.operation.bank.service;

import com.bank.operation.bank.model.dto.AccountDTO;
import com.bank.operation.bank.model.dto.TransactionHistoryDTO;
import com.bank.operation.bank.model.entity.Account;
import com.bank.operation.bank.model.entity.AccountPrincipal;
import com.bank.operation.bank.model.entity.TransactionHistory;
import com.bank.operation.bank.model.request.AccountCreationRequest;
import com.bank.operation.bank.repository.AccountRepository;
import com.bank.operation.bank.repository.TransactionHistoryRepository;
import com.bank.operation.utils.generator.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String createAccount(AccountCreationRequest accountCreationRequest) {
        double initialDeposit = accountCreationRequest.getInitialDeposit();
        validateInitialDeposit(accountCreationRequest);
        handleAccountNameExists(accountCreationRequest);

        accountCreationRequest.setAccountPassword(bCryptPasswordEncoder.encode(accountCreationRequest.getAccountPassword()));
        Account newAccount = new Account(accountCreationRequest, Generator.generateId(getAllAccounts()));
        accountRepository.save(newAccount);

        recordTransaction(initialDeposit, newAccount);
        return newAccount.getAccountNumber();
    }

    private void recordTransaction(double initialDeposit, Account newAccount) {
        var newTransactionHistory = getNewTransactionHistory(initialDeposit, newAccount);
        transactionHistoryRepository.save(newAccount, newTransactionHistory);
    }

    public List<TransactionHistoryDTO> getAccountStatement(String accountNumber) {
        var accountInfo = getAccount(accountNumber);
        return transactionHistoryRepository.findByAccountNumber(accountInfo.getAccountNumber())
                .stream().map(TransactionHistoryDTO::new).toList();
    }

    public AccountDTO getAccountInfo(String accountNumber) {
        Account account = getAccount(accountNumber);
        return new AccountDTO(account);
    }

    private TransactionHistory getNewTransactionHistory(double depositAmount, Account accountInfo) {
        return new TransactionHistory(accountInfo, "Deposit", depositAmount, "Deposit");
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found"));
    }

    public Map<String, Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong credentials"));
        return new AccountPrincipal(account);
    }

    private void validateInitialDeposit(AccountCreationRequest accountCreationRequest) {
        if (accountCreationRequest.getInitialDeposit() < 500)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A minimum deposit of #500 is required to create an account");
    }

    private void handleAccountNameExists(AccountCreationRequest accountCreationRequest) {
        if (accountRepository.accountNameExists(accountCreationRequest.getAccountName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account name exists");
    }

    public void updateAccount(Account accountInfo) {
        accountRepository.update(accountInfo);
    }
}

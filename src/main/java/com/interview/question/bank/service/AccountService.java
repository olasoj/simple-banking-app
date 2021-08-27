package com.interview.question.bank.service;

import com.interview.question.bank.model.dto.AccountDTO;
import com.interview.question.bank.model.dto.TransactionHistoryDTO;
import com.interview.question.bank.model.entity.Account;
import com.interview.question.bank.model.entity.AccountPrincipal;
import com.interview.question.bank.model.entity.TransactionHistory;
import com.interview.question.bank.model.request.AccountCreationRequest;
import com.interview.question.bank.repository.AccountRepository;
import com.interview.question.bank.repository.TransactionHistoryRepository;
import com.interview.question.utils.generator.Generator;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
        return "Account created successfully. Account number: " + newAccount.getAccountNumber();
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

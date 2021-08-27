package com.interview.question.bank.service;

import com.interview.question.bank.model.entity.Account;
import com.interview.question.bank.model.request.AccountCreationRequest;
import com.interview.question.bank.repository.AccountRepository;
import com.interview.question.bank.repository.TransactionHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;


    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount() {
        var accountCreationRequest = new AccountCreationRequest("Rob", "less", (double) 500);
        doNothing().when(accountRepository).save(any());
        doNothing().when(transactionHistoryRepository).save(any(), any());

        accountService.createAccount(accountCreationRequest);
        verify(accountRepository, times(1)).save(any());

    }

    @Test
    void getAccountStatement() {
        String accountNumber = mockGetAccount();

        accountService.getAccountStatement(accountNumber);
        verify(accountRepository, times(1)).findByAccountNumber(any());
    }

    @Test
    void getAccountInfo() {
        String accountNumber = mockGetAccount();

        accountService.getAccountInfo(accountNumber);
        verify(accountRepository, times(1)).findByAccountNumber(any());
    }

    private String mockGetAccount() {
        String accountNumber = "00000001";
        var accountCreationRequest = new AccountCreationRequest("Rob", "less", (double) 0);

        var account = new Account(accountCreationRequest, accountNumber);
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        return accountNumber;
    }
}
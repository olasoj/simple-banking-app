package com.interview.question.bank.service;

import com.interview.question.auth.AuthService;
import com.interview.question.bank.model.entity.Account;
import com.interview.question.bank.model.request.AccountCreationRequest;
import com.interview.question.bank.model.request.DepositRequest;
import com.interview.question.bank.model.request.WithdrawRequest;
import com.interview.question.bank.repository.TransactionHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @Mock
    private AuthService authService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void withdraw() {
        String accountNumber = mockGetAccount();
        var withdrawRequest = new WithdrawRequest(accountNumber, "less", (double) 98);
        mockUpdateAccount();
        doNothing().when(transactionHistoryRepository).save(any(), any());

        transactionService.withdraw(withdrawRequest);
        verify(transactionHistoryRepository, times(1)).save(any(), any());
    }

    @Test
    void deposit() {
        String accountNumber = mockGetAccount();
        var depositRequest = new DepositRequest(accountNumber, 100.0);

        mockUpdateAccount();
        doNothing().when(transactionHistoryRepository).save(any(), any());

        transactionService.deposit(depositRequest);
        verify(transactionHistoryRepository, times(1)).save(any(), any());
    }

    private String mockGetAccount() {
        String accountNumber = "00000001";
        var accountCreationRequest = new AccountCreationRequest("Rob", "less", (double) 799);

        var account = new Account(accountCreationRequest, accountNumber);
        when(accountService.getAccount(accountNumber)).thenReturn((account));
        return accountNumber;
    }

    private void mockUpdateAccount() {
        String accountNumber = "00000001";
        var accountCreationRequest = new AccountCreationRequest("Rob", "less", (double) 799);

        var account = new Account(accountCreationRequest, accountNumber);
        doNothing().when(accountService).updateAccount(account);
    }
}
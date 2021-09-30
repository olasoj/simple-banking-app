package com.bank.operation.bank.service;

import com.bank.operation.bank.repository.TransactionHistoryRepository;
import com.bank.operation.auth.AuthService;
import com.bank.operation.bank.model.entity.Account;
import com.bank.operation.bank.model.request.AccountCreationRequest;
import com.bank.operation.bank.model.request.DepositRequest;
import com.bank.operation.bank.model.request.WithdrawRequest;
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
        var account = mockGetAccount();
        var withdrawRequest = new WithdrawRequest(account.getAccountNumber(), "less", (double) 98);
        mockUpdateAccount(account);
        doNothing().when(transactionHistoryRepository).save(any(), any());

        transactionService.withdraw(withdrawRequest);
        verify(transactionHistoryRepository, times(1)).save(any(), any());
    }

    @Test
    void deposit() {
        var account = mockGetAccount();
        var depositRequest = new DepositRequest(account.getAccountNumber(), 100.0);

        mockUpdateAccount(account);
        doNothing().when(transactionHistoryRepository).save(any(), any());

        transactionService.deposit(depositRequest);
        verify(transactionHistoryRepository, times(1)).save(any(), any());
    }

    private void mockUpdateAccount(Account account) {
        doNothing().when(accountService).updateAccount(account);
    }

    private Account mockGetAccount() {
        String accountNumber = "00000001";
        var accountCreationRequest = new AccountCreationRequest("Rob", "less", (double) 799);

        var account = new Account(accountCreationRequest, accountNumber);
        when(accountService.getAccount(accountNumber)).thenReturn((account));
        return account;
    }

}
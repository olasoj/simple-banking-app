package com.interview.question.bank.service;

import com.interview.question.auth.AuthService;
import com.interview.question.bank.model.entity.Account;
import com.interview.question.bank.model.entity.TransactionHistory;
import com.interview.question.bank.model.request.DepositRequest;
import com.interview.question.bank.model.request.WithdrawRequest;
import com.interview.question.bank.repository.TransactionHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final AuthService authService;
    private final AccountService accountService;

    public String withdraw(WithdrawRequest withdrawRequest) {
        authService.authenticateUser(withdrawRequest.getAccountNumber(), withdrawRequest.getAccountPassword());
        double withdrawAmount = withdrawRequest.getWithdrawAmount();
        var accountInfo = accountService.getAccount(withdrawRequest.getAccountNumber());
        double newBalance = accountInfo.getBalance() - withdrawAmount;

        handleInsufficientBalance(newBalance);
        handleInvalidWithdrawalAmount(withdrawAmount);

        accountInfo.setBalance(newBalance);
        accountService.updateAccount(accountInfo);

        var newTransactionHistory = getNewTransactionHistory(withdrawAmount, accountInfo, "Withdraw ");
        transactionHistoryRepository.save(accountInfo, newTransactionHistory);
        return "Withdrawal successfully";
    }

    private void handleInvalidWithdrawalAmount(double withdrawAmount) {
        if (withdrawAmount < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not withdraw below  #1");
    }

    private void handleInsufficientBalance(double newBalance) {
        if (newBalance < 500)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds: Balance will be below #500. Balance " + newBalance);
    }

    public String deposit(DepositRequest depositRequest) {
        authService.authenticateUser(depositRequest.getAccountNumber(), depositRequest.getAccountPassword());
        double depositAmount = depositRequest.getAmount();
        var accountInfo = accountService.getAccount(depositRequest.getAccountNumber());
        double newBalance = accountInfo.getBalance() + depositAmount;

        handleInvalidDepositAmount(depositAmount);

        accountInfo.setBalance(newBalance);
        accountService.updateAccount(accountInfo);
        var newTransactionHistory = getNewTransactionHistory(depositAmount, accountInfo, "Deposit");
        transactionHistoryRepository.save(accountInfo, newTransactionHistory);
        return "Deposit successfully";
    }

    private void handleInvalidDepositAmount(double depositAmount) {
        if (depositAmount < 1 || depositAmount > 1000000)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not deposit an amount below #1 and above #1000000");
    }

    private TransactionHistory getNewTransactionHistory(double depositAmount, Account accountInfo, String operation) {
        return new TransactionHistory(accountInfo, operation, depositAmount, operation);
    }
}

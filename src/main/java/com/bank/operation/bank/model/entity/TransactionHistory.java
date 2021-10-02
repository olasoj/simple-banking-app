package com.bank.operation.bank.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionHistory implements Serializable {
    private LocalDateTime transactionDate = LocalDateTime.now();
    private String transactionType;
    private Double amount;
    private String narration;
    private Double accountBalance;

    public TransactionHistory(Account account, String transactionType, Double amount, String narration) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.narration = narration;
        this.accountBalance = account.getBalance();
    }
}

package com.bank.operation.bank.model.dto;

import com.bank.operation.bank.model.entity.TransactionHistory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionHistoryDTO implements Serializable {
    private LocalDate transactionDate = LocalDate.now();
    private String transactionType;
    private Double amount;
    private String narration;
    private Double accountBalance;

    public TransactionHistoryDTO(TransactionHistory transactionHistory) {
        this.transactionDate = transactionHistory.getTransactionDate();
        this.transactionType = transactionHistory.getTransactionType();
        this.amount = transactionHistory.getAmount();
        this.narration = transactionHistory.getNarration();
        this.accountBalance = transactionHistory.getAccountBalance();
    }
}

package com.interview.question.bank.repository;

import com.interview.question.bank.model.entity.Account;
import com.interview.question.bank.model.entity.TransactionHistory;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class TransactionHistoryRepository {
    private final Map<String, List<TransactionHistory>> transactions = new HashMap<>();
    private final List<TransactionHistory> transactionHistories = new ArrayList<>();

    public void save(Account account, TransactionHistory transactionHistory) {
        transactionHistories.add(transactionHistory);
        transactions.put(account.getAccountNumber(), transactionHistories);
    }

    public List<TransactionHistory> findByAccountNumber(String accountNumber) {
        return transactions.get(accountNumber);
    }
}

package com.bank.operation.bank.model.dto;

import com.bank.operation.bank.model.entity.TransactionHistory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionHistoryDTO implements Serializable {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    @JsonProperty("transactionDate")
    private LocalDateTime transactionDate;
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

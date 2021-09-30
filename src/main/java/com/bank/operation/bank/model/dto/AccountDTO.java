package com.bank.operation.bank.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.bank.operation.bank.model.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8938848488913773063L;
    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("accountPassword")
    private String accountNumber;

    @JsonProperty("balance")
    private Double balance;

    public AccountDTO(Account account) {
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }
}

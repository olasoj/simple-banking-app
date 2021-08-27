package com.interview.question.bank.model.entity;

import com.interview.question.bank.model.request.AccountCreationRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Component
public class Account implements Serializable {
    private String accountName;
    private String accountPassword;
    private Double balance;
    private String accountNumber;

    public Account(AccountCreationRequest accountCreationRequest, String accountNumber) {
        this.accountName = accountCreationRequest.getAccountName();
        this.accountPassword = accountCreationRequest.getAccountPassword();
        this.balance = accountCreationRequest.getInitialDeposit();
        this.accountNumber = accountNumber;
    }

}

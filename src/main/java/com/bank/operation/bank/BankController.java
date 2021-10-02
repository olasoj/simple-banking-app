package com.bank.operation.bank;

import com.bank.operation.bank.service.AccountService;
import com.bank.operation.bank.model.dto.AccountDTO;
import com.bank.operation.bank.model.dto.TransactionHistoryDTO;
import com.bank.operation.bank.model.entity.AccountPrincipal;
import com.bank.operation.bank.model.request.AccountCreationRequest;
import com.bank.operation.bank.model.request.DepositRequest;
import com.bank.operation.bank.model.request.WithdrawRequest;
import com.bank.operation.bank.service.TransactionService;
import com.bank.operation.utils.model.ResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class BankController {
    private AccountService bankService;
    private TransactionService transactionService;

    @GetMapping(value = "account/info/me")
    public ResponseEntity<Map<String, Object>> accountInfo(@AuthenticationPrincipal AccountPrincipal accountPrincipal
    ) {
        AccountDTO accountInfo = bankService.getAccountInfo(accountPrincipal.getAccountNumber());
        return ResponseEntity.ok().body(ResponseModel.getResponseBody(HttpStatus.OK,  accountInfo, "account"));
    }

    @GetMapping(value = "account/statement/me")
    public ResponseEntity<List<TransactionHistoryDTO>> accountStatement(@AuthenticationPrincipal AccountPrincipal accountPrincipal
    ) {
        List<TransactionHistoryDTO> accountStatement = bankService.getAccountStatement(accountPrincipal.getAccountNumber());
        return ResponseEntity.ok().body(accountStatement);
    }

    @PostMapping(value = "account/create")
    public ResponseEntity<Map<String, Object>> createAccount(@Valid @RequestBody AccountCreationRequest accountCreationRequest) {
        String accountCreationMessage = bankService.createAccount(accountCreationRequest);
        Map<String, Object> genericResponseModel = ResponseModel.getResponseBody(HttpStatus.OK, accountCreationMessage, null);
        return ResponseEntity.status(201).body(genericResponseModel);
    }

    @PostMapping(value = "transaction/withdrawal")
    public ResponseEntity<Map<String, Object>> withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        String withdrawSuccessMessage = transactionService.withdraw(withdrawRequest);
        Map<String, Object> genericResponseModel = ResponseModel.getResponseBody(HttpStatus.OK, withdrawSuccessMessage, null);
        return ResponseEntity.ok().body(genericResponseModel);
    }

    @PostMapping(value = "transaction/deposit")
    public ResponseEntity<Map<String, Object>> deposit(@Valid @RequestBody DepositRequest depositRequest) {
        String depositSuccessMsg = transactionService.deposit(depositRequest);
        Map<String, Object> genericResponseModel = ResponseModel.getResponseBody(HttpStatus.OK, depositSuccessMsg, null);
        return ResponseEntity.ok().body(genericResponseModel);
    }
}

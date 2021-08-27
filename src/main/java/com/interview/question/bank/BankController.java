package com.interview.question.bank;

import com.interview.question.bank.model.dto.AccountDTO;
import com.interview.question.bank.model.dto.TransactionHistoryDTO;
import com.interview.question.bank.model.request.AccountCreationRequest;
import com.interview.question.bank.model.request.DepositRequest;
import com.interview.question.bank.model.request.WithdrawRequest;
import com.interview.question.bank.service.AccountService;
import com.interview.question.bank.service.TransactionService;
import com.interview.question.utils.model.ResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping(value = "account_info/{accountNumber}")
    public ResponseEntity<Map<String, Object>> accountInfo(@PathVariable(value = "accountNumber") String accountNumber// @AuthenticationPrincipal UserDetails userPrincipal,
    ) {
        AccountDTO accountInfo = bankService.getAccountInfo(accountNumber);
        return ResponseEntity.ok().body(ResponseModel.responseWithAdditionalObject(HttpStatus.OK, "account", accountInfo));
    }

    @GetMapping(value = "account_statement/{accountNumber}")
    public ResponseEntity<List<TransactionHistoryDTO>> accountStatement(@PathVariable(value = "accountNumber") String accountNumber, @AuthenticationPrincipal UserDetails userPrincipal
    ) {
        List<TransactionHistoryDTO> accountStatement = bankService.getAccountStatement(accountNumber);
        return ResponseEntity.ok().body(accountStatement);
    }

    @PostMapping(value = "create_account")
    public ResponseEntity<Map<String, Object>> createAccount(@Valid @RequestBody AccountCreationRequest accountCreationRequest) {
        String accountCreationMessage = bankService.createAccount(accountCreationRequest);
        Map<String, Object> genericResponseModel = ResponseModel.getResponseBody(HttpStatus.OK, accountCreationMessage, null);
        return ResponseEntity.ok().body(genericResponseModel);
    }

    @PostMapping(value = "/withdrawal")
    public ResponseEntity<Map<String, Object>> withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        String withdrawSuccessMessage = transactionService.withdraw(withdrawRequest);
        Map<String, Object> genericResponseModel = ResponseModel.getResponseBody(HttpStatus.OK, withdrawSuccessMessage, null);
        return ResponseEntity.ok().body(genericResponseModel);
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<Map<String, Object>> deposit(@Valid @RequestBody DepositRequest depositRequest) {
        String depositSuccessMsg = transactionService.deposit(depositRequest);
        Map<String, Object> genericResponseModel = ResponseModel.getResponseBody(HttpStatus.OK, depositSuccessMsg, null);
        return ResponseEntity.ok().body(genericResponseModel);
    }
}

package com.bank.operation.bank;

import com.bank.operation.bank.model.dto.AccountDTO;
import com.bank.operation.bank.model.dto.TransactionHistoryDTO;
import com.bank.operation.bank.model.entity.AccountPrincipal;
import com.bank.operation.bank.model.request.AccountCreationRequest;
import com.bank.operation.bank.model.request.DepositRequest;
import com.bank.operation.bank.model.request.WithdrawRequest;
import com.bank.operation.bank.service.AccountService;
import com.bank.operation.bank.service.TransactionService;
import com.bank.operation.jwt.JwtTokenProvider;
import com.bank.operation.utils.model.model.Response;
import com.bank.operation.utils.model.transformer.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class BankController {
    private final AccountService bankService;
    private final TransactionService transactionService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping(value = "account/info/me")
    public ResponseEntity<Response<AccountDTO>> accountInfo(@AuthenticationPrincipal AccountPrincipal accountPrincipal
    ) {
        AccountDTO accountInfo = bankService.getAccountInfo(accountPrincipal.getAccountNumber());
        Response<AccountDTO> response = ResponseAssembler.toResponse(HttpStatus.OK, accountInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "account/statement/me")
    public ResponseEntity<List<TransactionHistoryDTO>> accountStatement(@AuthenticationPrincipal AccountPrincipal accountPrincipal
    ) {
        List<TransactionHistoryDTO> accountStatement = bankService.getAccountStatement(accountPrincipal.getAccountNumber());
        return ResponseEntity.ok().body(accountStatement);
    }

    @PostMapping(value = "account/create")
    public ResponseEntity<Response<String>> createAccount(@Valid @RequestBody AccountCreationRequest accountCreationRequest,
                                                          HttpServletResponse httpServletResponse) {
        String accountCreationMessage = bankService.createAccount(accountCreationRequest);
        String jwt = jwtTokenProvider.createJwt(accountCreationMessage);
        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, jwt);

        Response<String> response = ResponseAssembler.toResponse(HttpStatus.OK, accountCreationMessage);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping(value = "transaction/withdrawal")
    public ResponseEntity<Response<String>> withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        String withdrawSuccessMessage = transactionService.withdraw(withdrawRequest);
        Response<String> response = ResponseAssembler.toResponse(HttpStatus.OK, withdrawSuccessMessage);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "transaction/deposit")
    public ResponseEntity<Response<String>> deposit(@Valid @RequestBody DepositRequest depositRequest) {
        String depositSuccessMsg = transactionService.deposit(depositRequest);
        Response<String> response = ResponseAssembler.toResponse(HttpStatus.OK, depositSuccessMsg);
        return ResponseEntity.ok().body(response);
    }
}

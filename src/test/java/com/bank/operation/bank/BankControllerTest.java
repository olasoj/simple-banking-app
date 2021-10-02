package com.bank.operation.bank;

import com.bank.operation.bank.model.request.AccountCreationRequest;
import com.bank.operation.bank.model.request.DepositRequest;
import com.bank.operation.bank.model.request.WithdrawRequest;
import com.bank.operation.bank.service.AccountService;
import com.bank.operation.bank.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(MockitoExtension.class)
class BankControllerTest {

    private MockMvc mvc;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private BankController bankController;

    private JacksonTester<AccountCreationRequest> accountCreationRequestJacksonTester;
    private JacksonTester<DepositRequest> depositRequestJacksonTester;
    private JacksonTester<WithdrawRequest> withdrawRequestJacksonTester;


    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(bankController)
                .build();
    }

    @Test
    void accountInfo() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        get("/account/info/me")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(accountService, times(1)).getAccountInfo(anyString());

    }

    @Test
    void accountStatement() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        get("/account/statement/me")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(accountService, times(1)).getAccountStatement(anyString());
    }

    @Test
    void createAccount() throws Exception {
        var accountCreationRequest = new AccountCreationRequest("Rob", "less", (double) 0);
        JsonContent<AccountCreationRequest> write = accountCreationRequestJacksonTester.write(accountCreationRequest);
        MockHttpServletResponse response = mvc.perform(
                post("/account/create").
                        contentType(MediaType.APPLICATION_JSON).content(
                                write.getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(accountService, times(1)).createAccount(any());
    }

    @Test
    void withdraw() throws Exception {
        var withdrawRequest = new WithdrawRequest("Rob", "less", (double) 0);
        JsonContent<WithdrawRequest> write = withdrawRequestJacksonTester.write(withdrawRequest);
        MockHttpServletResponse response = mvc.perform(
                post("/transaction/withdrawal").
                        contentType(MediaType.APPLICATION_JSON).content(
                                write.getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(transactionService, times(1)).withdraw(any());
    }

    @Test
    void deposit() throws Exception {
        var depositRequest = new DepositRequest("Rob", 100.0);
        JsonContent<DepositRequest> write = depositRequestJacksonTester.write(depositRequest);
        MockHttpServletResponse response = mvc.perform(
                post("/transaction/deposit").
                        contentType(MediaType.APPLICATION_JSON).content(
                                write.getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(transactionService, times(1)).deposit(any());
    }
}
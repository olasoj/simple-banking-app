package com.bank.operation.bank.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated("jsonschema2pojo")
public class WithdrawRequest implements Serializable {

    private final static long serialVersionUID = -3594624925239206948L;
    @JsonProperty("accountNumber")
    @NotEmpty(message = "Account number is required")
    private String accountNumber;
    @NotEmpty(message = "Account password is required")
    private String accountPassword;
    @JsonProperty("withdrawnAmount")
    @NotNull(message = "Withdraw amount is required")
    private Double withdrawAmount;
}

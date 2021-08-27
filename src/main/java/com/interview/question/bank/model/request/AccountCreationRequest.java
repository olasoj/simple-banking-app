package com.interview.question.bank.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreationRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8938848488913773063L;
    @JsonProperty("accountName")
    @NotEmpty(message = "Account name is required")
    private String accountName;

    @JsonProperty("accountPassword")
    @NotEmpty(message = "Account password is required ")
    private String accountPassword;

    @JsonProperty("initialDeposit")
    @NotNull(message = "An initial deposit is required ")
    private Double initialDeposit;
}

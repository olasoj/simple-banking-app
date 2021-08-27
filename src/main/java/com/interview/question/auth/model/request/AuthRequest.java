package com.interview.question.auth.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest implements Serializable {
    @NotNull(message = "Account number is required")
    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountPassword")
    @NotNull(message = "Account password is required")
    private String password;
}

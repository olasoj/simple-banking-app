package com.interview.question.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class AuthDTO implements Serializable {
    @JsonProperty("username")
    private String username;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("access_token_expiration")
    private int accessTokenExpiration;

    @JsonProperty("refresh_token_expiration")
    private int refreshTokenExpiration;

    public AuthDTO(String username, String jwt, String refreshToken, int jwtExpiration, int refreshTokenExpiration) {
        this.username = username;
        this.accessToken = jwt;
        this.refreshToken = refreshToken;
        this.accessTokenExpiration = jwtExpiration / 1000;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }
}

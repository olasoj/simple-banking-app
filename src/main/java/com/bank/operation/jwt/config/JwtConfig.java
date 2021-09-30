package com.bank.operation.jwt.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class JwtConfig {
    @Value("${app.jwt.secret-key}")
    private String secretKey;

    @Value("${app.jwt.expire-length}")
    private int expireLength;
}

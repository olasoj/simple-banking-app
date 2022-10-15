package com.bank.operation.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
      private final JwtTokenVerifier jwtTokenVerifier;

      @Override
      public void configure(HttpSecurity http) {
            http.addFilterAfter(jwtTokenVerifier, BasicAuthenticationFilter.class);
      }
}

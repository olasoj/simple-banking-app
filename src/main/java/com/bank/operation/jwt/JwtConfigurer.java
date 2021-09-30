package com.bank.operation.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@AllArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
      private final JwtTokenProvider jwtTokenProvider;

      @Override
      public void configure(HttpSecurity http) {
            var jwtTokenVerifier = new JwtTokenVerifier(jwtTokenProvider);
            http.addFilterAfter(jwtTokenVerifier, BasicAuthenticationFilter.class);
      }
}

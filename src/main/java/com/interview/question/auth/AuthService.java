package com.interview.question.auth;

import com.interview.question.auth.model.request.AuthRequest;
import com.interview.question.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String generateAuthCredentials(AuthRequest authRequest) {
        authenticateUser(authRequest.getAccountNumber(), authRequest.getPassword());
        return jwtTokenProvider.createJwt(authRequest.getAccountNumber());
    }

    public void authenticateUser(String accountNumber, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountNumber, password));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong credentials");
        }
    }


}

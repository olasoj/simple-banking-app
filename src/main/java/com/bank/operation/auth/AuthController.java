package com.bank.operation.auth;

import com.bank.operation.auth.model.request.AuthRequest;
import com.bank.operation.utils.model.model.Response;
import com.bank.operation.utils.model.transformer.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<Response<String>> login(@Valid @RequestBody AuthRequest authRequest) {
        String authCredentials = authService.generateAuthCredentials(authRequest);
        Response<String> response = ResponseAssembler.toResponse(HttpStatus.OK, authCredentials);

        return ResponseEntity.ok().body(response);
    }
}

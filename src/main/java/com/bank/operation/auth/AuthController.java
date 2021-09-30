package com.bank.operation.auth;

import com.bank.operation.utils.model.ResponseModel;
import com.bank.operation.auth.model.request.AuthRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().body(ResponseModel.getResponseBody(HttpStatus.OK, authService.generateAuthCredentials(authRequest), "accessToken"));
    }
}

package com.bank.operation.utils.route;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public enum OpenRoutes {
    BANK_CONTROLLER(List.of("POST"), "/api/v1/create_account"),
    AUTH_CONTROLLER(Collections.singletonList("POST"), "/api/v1/login");

    private final String route;
    private final List<String> methods;

    OpenRoutes(List<String> methods, String route) {
        this.methods = methods;
        this.route = route;
    }
}

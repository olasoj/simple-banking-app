package com.bank.operation.utils.route;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public enum OpenRoutes {
    BANK_CONTROLLER(List.of("POST"), "/create_account"),
    AUTH_CONTROLLER(Collections.singletonList("POST"), "/login");

    private final String route;
    private final List<String> methods;

    OpenRoutes(List<String> methods, String route) {
        this.methods = methods;
        this.route = route;
    }
}

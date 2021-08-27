package com.interview.question.utils.route;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OpenRouteModel {
    private String route;
    private List<String> methods;

    public OpenRouteModel(String route, List<String> methods){
        this.route=route;
        this.methods=methods;
    }
}

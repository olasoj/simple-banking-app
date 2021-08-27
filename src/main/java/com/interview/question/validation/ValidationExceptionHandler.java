package com.interview.question.validation;

import com.interview.question.utils.model.ResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValid(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) {
        var errors = getErrors(ex);
        ResponseModel.responseValidationError(response, errors, "Validation failed", HttpStatus.BAD_REQUEST);
    }

    private Map<Object, Object> getErrors(MethodArgumentNotValidException ex) {
        var errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}

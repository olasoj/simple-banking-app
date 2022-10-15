package com.bank.operation.validation;

import com.bank.operation.utils.model.ResponseModel;
import com.bank.operation.utils.model.model.Response;
import com.bank.operation.utils.model.model.ResponseError;
import com.bank.operation.utils.model.transformer.ResponseAssembler;
import com.bank.operation.utils.model.transformer.ResponseErrorAssembler;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ValidationExceptionHandler {
    private final ResponseModel responseModel;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValid(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) {
        var errors = getErrors(ex);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(errors, "Validation failed", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.INTERNAL_SERVER_ERROR, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    private Map<String, Object> getErrors(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, Object>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}

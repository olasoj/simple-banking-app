package com.interview.question.exception;

import com.interview.question.utils.model.ResponseModel;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        var errorAttributes = super.getErrorAttributes(webRequest, options);
        HttpStatus status = HttpStatus.valueOf((Integer) errorAttributes.get("status"));
        String message = (String) errorAttributes.get("message");
        return ResponseModel.getResponseBody(status, message, null);
    }
}

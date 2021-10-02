package com.bank.operation.utils.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseModel.class);
    private static final String RESPONSE_MESSAGE = "Failed to process response message";

    public static void responseValidationError(HttpServletResponse response, Map<Object, Object> errors, String message, HttpStatus status) {
        Map<String, Object> responseMessage = getResponseBody(status, message, null);
        responseMessage.put("errors", errors);
        responseConfig(response, status, responseMessage);
    }

    public static Map<String, Object> getResponseBody(HttpStatus status, Object message, String messageKey) {
        Map<String, Object> responseMessage = new HashMap<>();

        String resolvedMessageKey = Objects.isNull(messageKey) ? "message" : messageKey;
        var successful = (status.is2xxSuccessful());
        responseMessage.put("timestamp", getTimeStamp());
        responseMessage.put("status", status.value());
        responseMessage.put("success", successful);
        responseMessage.put(resolvedMessageKey, message);
        return responseMessage;
    }

    public static void getResponseBody(HttpServletResponse response, HttpStatus status, String message) {
        Map<String, Object> genericResponseModel = getResponseBody(status, message, null);
        responseConfig(response, status, genericResponseModel);
    }

    private static void responseConfig(HttpServletResponse response, HttpStatus status, Map<String, Object> responseMessage) {
        try {
            var mapper = new ObjectMapper();
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8.name());
            mapper.writeValue(response.getWriter(), responseMessage);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, RESPONSE_MESSAGE);
        }
    }

    private static String getTimeStamp() {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(dateTimeFormatter);
    }


}

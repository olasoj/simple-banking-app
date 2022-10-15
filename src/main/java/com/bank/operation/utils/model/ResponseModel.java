package com.bank.operation.utils.model;

import com.bank.operation.utils.model.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ResponseModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseModel.class);
    private static final String RESPONSE_MESSAGE = "Failed to process response message";
    private final ObjectMapper objectMapper;

    public ResponseModel(@Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> void writeResponse(HttpServletResponse response, Response<T> responseMessage) {
        try {
            response.setStatus(responseMessage.getStatusCode());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8.name());
            objectMapper.writeValue(response.getWriter(), responseMessage);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, RESPONSE_MESSAGE);
        }
    }

}

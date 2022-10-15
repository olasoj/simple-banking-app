package com.bank.operation.utils.model.transformer;

import com.bank.operation.utils.model.model.Response;
import com.bank.operation.utils.model.model.ResponseStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseAssembler {

    public static <T> Response<T> toResponse(HttpStatus httpStatus, T data) {
        ResponseStatus responseStatus = responseState(httpStatus);

        Response.ResponseBuilder<T> builder = Response.builder();
        return builder
                .withResponseStatus(responseStatus)
                .withTimestamp(getTimeStamp())
                .withStatusCode(httpStatus.value())
                .withData(data)
                .build();
    }

    private static ResponseStatus responseState(HttpStatus httpStatus) {
        if (HttpStatus.ACCEPTED.equals(httpStatus)) return ResponseStatus.PENDING;
        return httpStatus.is2xxSuccessful() ? ResponseStatus.SUCCESS : ResponseStatus.FAILED;
    }

    private static String getTimeStamp() {
        return Instant.now().toString();
    }
}

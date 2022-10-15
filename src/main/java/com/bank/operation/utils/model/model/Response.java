package com.bank.operation.utils.model.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"status", "timestamp", "statusCode", "data"})
@Generated("jsonschema2pojo")
public class Response<T> implements Serializable {
    private final static long serialVersionUID = -4886351106258078331L;
    @NonNull
    @JsonProperty("status")
    private ResponseStatus responseStatus;

    @NonNull
    @JsonProperty("timestamp")
    private String timestamp;

    @NonNull
    @JsonProperty("statusCode")
    private Integer statusCode;

    @NonNull
    @JsonProperty("data")
    private T data;

    public Response(ResponseBuilder<T> responseBuilder) {
        this.responseStatus = responseBuilder.responseStatus;
        this.timestamp = responseBuilder.timestamp;
        this.statusCode = responseBuilder.statusCode;
        this.data = responseBuilder.data;
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<T>();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.responseStatus)
                .append(this.timestamp)
                .append(this.statusCode)
                .append(this.data)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Response<?>))
            return false;

        final Response<?> response = (Response<?>) obj;

        return new EqualsBuilder()
                .append(this.responseStatus, response.responseStatus)
                .append(this.timestamp, response.timestamp)
                .append(this.statusCode, response.statusCode)
                .append(this.data, response.data)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("responseStatus", this.responseStatus)
                .append("timestamp", this.timestamp)
                .append("statusCode", this.statusCode)
                .append("data", this.data)
                .toString();
    }

    public static class ResponseBuilder<B> {
        private ResponseStatus responseStatus;
        private String timestamp;
        private Integer statusCode;
        private B data;

        public ResponseBuilder<B> withResponseStatus(ResponseStatus responseStatus) {
            this.responseStatus = responseStatus;
            return this;
        }

        public ResponseBuilder<B> withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResponseBuilder<B> withStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }


        public ResponseBuilder<B> withData(B data) {
            this.data = data;
            return this;
        }

        public Response<B> build() {
            return new Response<>(this);
        }
    }
}
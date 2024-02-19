package dev.marvin.savings.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ErrorResponse(
        @JsonProperty(value = "request status")
        String status,

        @JsonProperty(value = "response message")
        String message,

        @JsonProperty(value = "request time")
        LocalDateTime time
) {}

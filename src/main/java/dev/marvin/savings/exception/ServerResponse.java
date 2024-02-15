package dev.marvin.savings.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServerResponse(
        @JsonProperty(value = "request status")
        String status,

        @JsonProperty(value = "response message")
        String message) {
}

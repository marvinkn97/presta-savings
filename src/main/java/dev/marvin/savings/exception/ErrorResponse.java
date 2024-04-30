package dev.marvin.savings.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record ErrorResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yy hh:mm:ss", timezone = "UTC+3")
        Date timestamp,
        Integer httpStatusCode,
        String reason,
        String message) {
}

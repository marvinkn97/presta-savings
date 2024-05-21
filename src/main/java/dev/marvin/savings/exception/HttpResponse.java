package dev.marvin.savings.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record HttpResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yy hh:mm:ss", timezone = "Africa/Nairobi")
        Date timestamp,
        Integer status,
        String reason,
        String message) {
}

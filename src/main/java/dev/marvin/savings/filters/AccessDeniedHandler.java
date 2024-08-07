package dev.marvin.savings.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marvin.savings.config.AppResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Component
@Slf4j
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.FORBIDDEN.value())
                .reason(HttpStatus.FORBIDDEN.getReasonPhrase())
                .data("You do not have permission to access this data")
                .build();

        OutputStream outputStream = response.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, appResponse);
        outputStream.flush();
    }
}

package dev.marvin.savings.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marvin.savings.config.AppResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.UNAUTHORIZED.value())
                .reason(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .data("You need to be successfully authenticated to access the system")
                .build();

        OutputStream outputStream = response.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, appResponse);
        outputStream.flush();
    }
}

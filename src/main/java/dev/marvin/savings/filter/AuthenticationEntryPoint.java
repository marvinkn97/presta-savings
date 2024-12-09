package dev.marvin.savings.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marvin.savings.dto.AppResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        AppResponse<String> appResponse = new AppResponse<>(HttpStatus.FORBIDDEN.getReasonPhrase(), "You need to be successfully authenticated to access the system");
        OutputStream outputStream = response.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, appResponse);
        outputStream.flush();
    }
}

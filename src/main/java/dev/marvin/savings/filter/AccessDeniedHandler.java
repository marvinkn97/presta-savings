package dev.marvin.savings.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marvin.savings.dto.AppResponse;
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

@Component
@Slf4j
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        AppResponse<String> appResponse = new AppResponse<>(HttpStatus.FORBIDDEN.getReasonPhrase(), "You do not have permission to access this data");

        OutputStream outputStream = response.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, appResponse);
        outputStream.flush();
    }
}

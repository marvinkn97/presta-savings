package dev.marvin.savings.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marvin.savings.config.AppResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(authorizationHeader);

        if (ObjectUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var token = authorizationHeader.substring(7);
            boolean validToken = jwtService.validateJwtToken(token);

            if (!validToken) {
                throw new RuntimeException("invalid token");
            }

            var userDetails = jwtService.extractUserDetailsFromToken(token);

            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                filterChain.doFilter(request, response);
            }

            var existingUser = userDetailsService.loadUserByUsername(userDetails.get("username").toString());

            System.out.println(existingUser);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    existingUser, null, existingUser.getAuthorities()
            );

            System.out.println(usernamePasswordAuthenticationToken);

            WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
            usernamePasswordAuthenticationToken.setDetails(webAuthenticationDetails);

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            System.out.println(usernamePasswordAuthenticationToken);
            authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Auth: " + (authentication == null));


        } catch (Exception e) {
            log.info(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            AppResponse appResponse = AppResponse.builder()
                    .timestamp(new Date())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .reason(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .data("Invalid token")
                    .build();

            OutputStream outputStream = response.getOutputStream();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, appResponse);

            outputStream.flush();
            return;
        }
        filterChain.doFilter(request, response);

    }

}

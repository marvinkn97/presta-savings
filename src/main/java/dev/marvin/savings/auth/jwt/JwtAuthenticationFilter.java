package dev.marvin.savings.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            var token = authorizationHeader.substring(7);
            boolean validToken = jwtService.validateJwtToken(token);

            if (!validToken) {
                throw new RuntimeException("token is invalid");
            }

            var userDetails = jwtService.extractUserDetailsFromToken(token);

            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {

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
                System.out.println("Auth: "+(authentication == null));
            }
        } catch (Exception e) {
              response.setHeader("Error", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}

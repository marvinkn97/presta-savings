package dev.marvin.savings.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Key key = Keys.hmacShaKeyFor(SecurityConstraints.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            Map<String, Object> claims = new HashMap<>();
            claims.put("username", authentication.getName());
            claims.put("authorities", commaSeparatedStringFromAuthorityList(authentication.getAuthorities()));

            String jwt = Jwts.builder()
                    .setIssuer("PRESTA SAVINGS")
                    .setSubject(authentication.getName())
                    .addClaims(claims)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 86_400_000))
                    .signWith(key)
                    .compact();

            System.out.println(jwt);

            response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/auth");
    }

    private String commaSeparatedStringFromAuthorityList(Collection<? extends GrantedAuthority> grantedAuthorities) {
        Set<String> authorities = new HashSet<>();

        for (var authority : grantedAuthorities) {
            authorities.add(authority.getAuthority());
        }
        return String.join("", authorities);
    }
}

package dev.marvin.savings.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String secret = "fe6e22b5-9e4c-4a9f-a2a8-93d23f442b4c";

    private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public String generateJwtToken(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {

            var expirationDurationMs = 60 * 60 * 1000;
            var expirationDate = new Date().getTime() + expirationDurationMs;

            var role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst();

            Map<String, Object> claimsMap = new HashMap<>();

            role.ifPresent(s -> claimsMap.put("role", s));

            return Jwts.builder()
                    .setIssuer("savings@presta")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(expirationDate))
                    .setSubject(authentication.getName())
                    .addClaims(claimsMap)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } else throw new BadCredentialsException("Bad Credentials");
    }

    private Claims extractClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}

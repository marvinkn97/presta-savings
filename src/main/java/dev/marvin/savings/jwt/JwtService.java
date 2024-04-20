package dev.marvin.savings.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    public String generateToken(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Key key = Keys.hmacShaKeyFor(SecurityConstraints.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            Map<String, Object> claims = new HashMap<>();
            claims.put("username", authentication.getName());
            claims.put("authorities", authentication.getAuthorities());

            String jwt = Jwts.builder()
                    .setIssuer("PRESTA SAVINGS")
                    .setSubject(authentication.getName())
                    .addClaims(claims)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 86_400_000))
                    .signWith(key)
                    .compact();

            System.out.println(jwt);
            return jwt;

        }
        return "error";
    }
}

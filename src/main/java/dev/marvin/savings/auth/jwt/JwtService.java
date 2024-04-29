package dev.marvin.savings.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public final String generateJwtToken(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException("Not Authenticated");
        }

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
                .signWith(key)
                .compact();
    }

    public final Map<String, Object> extractUserDetailsFromToken(String token) {
        var claims = extractClaimsFromToken(token);

        var username = claims.getSubject();
        var role = claims.get("role", String.class);

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", username);
        userDetails.put("role", role);

        return userDetails;
    }

    private Claims extractClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public final boolean validateJwtToken(String token) {
        try {
            //extract claims from token
            var claims = extractClaimsFromToken(token);

            //extract check token issuer
            var tokenIssuer = claims.getIssuer();

            if (!tokenIssuer.equals("savings@presta")) {
                return false;
            }

            //extract and check expiration date
            var expirationDate = claims.getExpiration();

            return !expirationDate.before(new Date());

        } catch (Exception e) {
            throw new RuntimeException("error");
        }
    }
}
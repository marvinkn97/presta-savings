package dev.marvin.savings.service;

import dev.marvin.savings.appuser.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
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

        var expirationDurationMs = 60 * 60 * 1000;
        var expirationDate = new Date().getTime() + expirationDurationMs;

        var role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst();

        Map<String, Object> claimsMap = new HashMap<>();

        role.ifPresent(s -> claimsMap.put("role", s));

        role.ifPresent(s -> {
            if(s.equals("CUSTOMER")){
              var userDetails = (AppUser) authentication.getPrincipal();
                System.out.println(userDetails.getCustomer().getMemberNumber());
                claimsMap.put("memNo", userDetails.getCustomer().getMemberNumber());
            }
        });

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
                log.info("Issuer does not match");
                return false;
            }

            //extract and check expiration date
            var expirationDate = claims.getExpiration();

            if (expirationDate.before(new Date())) {
                log.info("token expired");
                return false;
            }

            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}

package dev.marvin.savings.config;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public String extractUsername(String jwtToken) {
        return null;

    }

    public Claims extractAllClaims(String jwtToken) {
        return null;
    }

}

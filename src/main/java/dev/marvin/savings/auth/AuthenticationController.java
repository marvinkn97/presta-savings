package dev.marvin.savings.auth;

import dev.marvin.savings.auth.jwt.JwtService;
import dev.marvin.savings.exception.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ServerResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateJwtToken(authentication);

        ServerResponse response = ServerResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(token)
                .build();

        return ResponseEntity.ok(response);
    }

}

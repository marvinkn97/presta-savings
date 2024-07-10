package dev.marvin.savings.controller;

import dev.marvin.savings.service.JwtService;
import dev.marvin.savings.config.AppResponse;
import dev.marvin.savings.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<AppResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password()));
        String token = null;

        if(ObjectUtils.isNotEmpty(authentication) && authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtService.generateJwtToken(authentication);
            log.info("Generated Token: {}", token);
        }

        AppResponse response = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(token)
                .build();

        return ResponseEntity.ok(response);
    }

}

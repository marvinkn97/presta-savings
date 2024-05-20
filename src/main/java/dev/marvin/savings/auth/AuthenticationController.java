package dev.marvin.savings.auth;

import dev.marvin.savings.auth.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @RequestMapping(value = "/register/confirm", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmEmailToken(@RequestParam(name = "token") String token) {
        authenticationService.confirmEmailToken(token);
        return ResponseEntity.ok("Email Confirmed Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateJwtToken(authentication);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}

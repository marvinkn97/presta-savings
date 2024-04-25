package dev.marvin.savings.auth;

import dev.marvin.savings.jwt.JwtService;
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
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String registerCustomer(@RequestBody RegistrationRequest registrationRequest){
       return authService.registerCustomer(registrationRequest);
    }

    @RequestMapping(value = "/register/confirm", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmEmailToken(@RequestParam(name = "token") String token){
        System.out.println(token);
        return authService.confirmEmailToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        System.out.println(token);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}

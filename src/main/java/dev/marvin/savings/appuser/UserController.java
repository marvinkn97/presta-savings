package dev.marvin.savings.appuser;

import dev.marvin.savings.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Resource", description = "CRUD REST API for User Management")
public class UserController {
    private final UserService userService;

    @PostMapping
//    @PreAuthorize(value = "hasAuthority('USER_CREATE')")
    @Operation(method = "POST", summary = "Create User", description = "Create User is used to save user in database")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Object> createUser(@RequestBody UserRegistrationRequest registrationRequest) {
        try {
            User savedUser = userService.createUser(registrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(2, TimeUnit.MINUTES))
                .body(users);
    }

}

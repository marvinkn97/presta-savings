package dev.marvin.savings.appuser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Resource", description = "CRUD REST API for User Management")
public class UserController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('USER_CREATE')")
    @Operation(method = "POST", summary = "Create User", description = "Create User is used to save user in database")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('USER_READ')")
    public ResponseEntity<List<User>> getAllUsers(){
       List<User> users = userService.getAllUsers();
       return ResponseEntity.ok(users);
    }
}

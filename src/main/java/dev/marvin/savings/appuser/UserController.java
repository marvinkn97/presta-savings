package dev.marvin.savings.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize(value = "hasAuthority('USER_CREATE')")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/all")
    @PreAuthorize(value = "hasAuthority('USER_READ')")
    public ResponseEntity<List<User>> getAllUsers(){
       List<User> users = userService.getAllUsers();
       return ResponseEntity.ok(users);
    }
}

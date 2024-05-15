package dev.marvin.savings.appuser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Resource", description = "User Management")
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @Operation(method = "GET", description = "GET ALL APP_USERS")
    public ResponseEntity<List<AppUser>> getAllAppUsers() {
        List<AppUser> users = appUserService.getAllAppUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}

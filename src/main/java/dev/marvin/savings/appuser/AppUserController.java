package dev.marvin.savings.appuser;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Resource", description = "User Management")
public class AppUserController {
    private final AppUserService appUserService;
    @GetMapping
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<List<AppUser>> getAllAppUsers() {
        List<AppUser> users = appUserService.getAllAppUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(2, TimeUnit.MINUTES))
                .body(users);
    }


}

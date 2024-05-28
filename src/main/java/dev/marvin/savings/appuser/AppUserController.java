package dev.marvin.savings.appuser;

import dev.marvin.savings.config.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "AppUser Resource", description = "User Management APIs")
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @Operation(method = "GET", description = "GET ALL APP_USERS")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<ServerResponse> getAllAppUsers() {

        List<AppUser> users = appUserService.getAllAppUsers();

        ServerResponse response = ServerResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(users)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

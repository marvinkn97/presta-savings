package dev.marvin.savings.appuser;

import dev.marvin.savings.dto.AppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@Tag(name = "AppUser Resource", description = "User Management API")
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @Operation(method = "GET", description = "Get All AppUsers")
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = AppResponse.class))})
    public ResponseEntity<AppResponse> getAllAppUsers() throws InterruptedException {

        Thread.sleep(1000);

        List<AppUserResponse> users = appUserService.getAllAppUsers();

        AppResponse response = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(users)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @Operation(method = "GET", description = "Get AppUser By ID")
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = AppResponse.class))})
    public ResponseEntity<AppResponse> getAppUserById(@PathVariable("userId") Integer id) {

        var user = appUserService.getAppUserById(id);

        AppResponse response = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(user)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.config.AppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
@Tag(name = "Customer Resource", description = "Customer Management")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/registration")
    @Operation(method = "POST", description = "Register Customer")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201", description = "CREATED", content = {@Content(schema = @Schema(implementation = AppResponse.class))}),
                    @ApiResponse(responseCode = "409", description = "CONFLICT", content = {@Content(schema = @Schema(implementation = AppResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {@Content(schema = @Schema(implementation = AppResponse.class))})})
    public ResponseEntity<AppResponse> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
        var response = customerService.registerCustomer(registrationRequest);

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.CREATED.value())
                .reason(HttpStatus.CREATED.getReasonPhrase().toUpperCase())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(appResponse);
    }

    @PostMapping("/registration/confirm")
    @Operation(method = "POST", description = "Confirm Email")
    public ResponseEntity<AppResponse> confirmEmailToken(@RequestParam(name = "token") String token) {
        customerService.confirmEmailToken(token);

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data("Email Confirmed Successfully")
                .build();

        return ResponseEntity.ok(appResponse);
    }

    @PostMapping("/registration/resend")
    @Operation(method = "POST", description = "Resend Email")
    public ResponseEntity<AppResponse> resendEmailToken(@RequestParam(name = "email") String email) {
        customerService.refreshConfirmationToken(email);

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data("Email Sent Successfully")
                .build();

        return ResponseEntity.ok(appResponse);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CSR')")
    @Operation(method = "GET", description = "Get All Customers")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = AppResponse.class))}),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {@Content(schema = @Schema(implementation = AppResponse.class))})})
    public ResponseEntity<AppResponse> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();

        Map<String,Object> payload = new HashMap<>();

        AppResponse response = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(customers)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberNumber}")
    @PreAuthorize("hasAnyAuthority('CSR','CUSTOMER')")
    @Operation(method = "GET", description = "Get One Customer")
    public ResponseEntity<AppResponse> getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        var customer = customerService.getCustomerByMemberNumber(memberNumber);
        AppResponse response = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{memberNumber}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(method = "PUT", description = "Update Customer Details")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {@Content(schema = @Schema(implementation = AppResponse.class))}),
                    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = AppResponse.class))}),
                    @ApiResponse(responseCode = "409", description = "CONFLICT", content = {@Content(schema = @Schema(implementation = AppResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {@Content(schema = @Schema(implementation = AppResponse.class))})})
    public ResponseEntity<AppResponse> updateCustomer(@PathVariable("memberNumber") String memberNumber, @Valid @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(memberNumber, updateRequest);

        System.out.println(updateRequest);

        AppResponse response = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data("customer updated successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{memberNumber}")
    @PreAuthorize(value = "hasAuthority('CUSTOMER')")
    @Operation(method = "DELETE", description = "Delete Customer")
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = AppResponse.class))})
    public ResponseEntity<AppResponse> deleteCustomer(@PathVariable("memberNumber") String memberNumber) {
        customerService.deleteCustomer(memberNumber);

        AppResponse response = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data("customer deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }

}



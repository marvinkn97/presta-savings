package dev.marvin.savings.customer.dto;

import lombok.Builder;

@Builder
public class CustomerResponse {
    String memberNumber;
    String name;
    String email;
    String mobile;
    Integer governmentId;
    String roleName;
}

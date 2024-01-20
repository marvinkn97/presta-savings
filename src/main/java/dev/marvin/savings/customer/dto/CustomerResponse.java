package dev.marvin.savings.customer.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerResponse {
    String memberNumber;
    String name;
    String email;
    String mobile;
    Integer governmentId;
}

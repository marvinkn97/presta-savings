package dev.marvin.savings.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    String memberNumber;
    String name;
    String role;
    String email;
    Integer mobile;
    Integer governmentId;
    Long createdDate;
}

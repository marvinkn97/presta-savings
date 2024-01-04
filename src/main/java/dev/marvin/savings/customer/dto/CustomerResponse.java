package dev.marvin.savings.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    String name;
    String email;
    String mobile;
    String governmentId;
    String memberNumber;
}

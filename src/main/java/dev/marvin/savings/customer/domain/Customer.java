package dev.marvin.savings.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer{
    private Integer id;
    private String name;
    private String email;
    private Long mobile;
    private Integer governmentId;
    private String memberNumber;
}

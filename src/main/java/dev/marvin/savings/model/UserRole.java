package dev.marvin.savings.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class UserRole implements Serializable {
    private Integer userRoleId;
    private Customer customer;
    private Role role;
}

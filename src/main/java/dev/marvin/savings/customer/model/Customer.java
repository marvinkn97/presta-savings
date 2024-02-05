package dev.marvin.savings.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
    private String memberNumber;
    private String name;
    private String email;
    private String password;
    private String mobile;
    private Integer governmentId;
    private Long createdDate;
    private String profilePic;
    private Role role;
}

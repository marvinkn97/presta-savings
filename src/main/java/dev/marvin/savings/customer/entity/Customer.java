package dev.marvin.savings.customer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Customer implements Serializable {
    private String memberNumber;
    private String name;
    private String email;  //unique
    private String password;
    private Role role;
    private Integer mobile;
    private Integer governmentId;
    private Long createdDate;


//    CustomerUtil.generateCustomerMemberNumber()
//    System.currentTimeMillis()
}

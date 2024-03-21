package dev.marvin.savings.customer;

import dev.marvin.savings.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Customer {
    private String memberNumber;
    private String name;
    private String mobile;
    private Integer governmentId;
    private String profileImageId;
    private User user;
}

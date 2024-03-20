package dev.marvin.savings.customer;

import dev.marvin.savings.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Customer extends User {
    private String memberNumber;
    private String mobile;
    private Integer governmentId;
    private String profileImageId;

    public Customer(User user) {
     super(user.getName(), user.getEmail(), user.getPassword());
    }

}

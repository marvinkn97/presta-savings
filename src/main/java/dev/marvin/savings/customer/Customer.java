package dev.marvin.savings.customer;

import dev.marvin.savings.appuser.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Customer implements Serializable {
    private Integer id;
    private String memberNumber;
    private Integer governmentId;
    private String profileImageId;
    private User user;
}

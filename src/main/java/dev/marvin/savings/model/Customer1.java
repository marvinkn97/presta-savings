package dev.marvin.savings.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Customer1 implements Serializable {
    private String memberNumber;
    private String mobile;
    private Integer governmentId;
    private String profileImageId;
    private User user;
}

package dev.marvin.savings.customer.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer implements Serializable {
    private String memberNumber;
    private String name;
    private String email;
    private String password;
    private String mobile;
    private Integer governmentId;
    private Long createdDate;
    private Long updatedDate;
    private String profileImageId;
}

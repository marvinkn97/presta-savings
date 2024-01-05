package dev.marvin.savings.customer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Customer implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Integer mobile;
    private Integer governmentId;
    private String memberNumber;
    private LocalDateTime createdAt;
}

package dev.marvin.savings.customer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Customer implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private Integer mobile;
    private Integer governmentId;
    private String memberNumber;
}

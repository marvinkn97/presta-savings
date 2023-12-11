package dev.marvin.savings.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@RequiredArgsConstructor
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

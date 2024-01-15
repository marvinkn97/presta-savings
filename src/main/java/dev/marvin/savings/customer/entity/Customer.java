package dev.marvin.savings.customer.entity;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
public class Customer implements Serializable {
    private String memberNumber;
    private String name;
    private String email;
    private String password;
    private Integer mobile;
    private Integer governmentId;
    private Long createdDate;
    private Deleted isDeleted;

//TODO:
// add photo property
// System.currentTimeMillis()
}

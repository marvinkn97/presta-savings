package dev.marvin.savings.customer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Customer implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private Integer mobile;
    private Integer governmentId;
    private String memberNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer() {
    }


}

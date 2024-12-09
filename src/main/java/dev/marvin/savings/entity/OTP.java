package dev.marvin.savings.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_otp")
@NoArgsConstructor
@Getter
@Setter
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mobile;
    private String otp;
    private LocalDateTime createdAt;
    private LocalDateTime expiryTime;
}
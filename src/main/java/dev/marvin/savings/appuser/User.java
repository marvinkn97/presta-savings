package dev.marvin.savings.appuser;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_users", uniqueConstraints = {@UniqueConstraint(name = "email_id_unique", columnNames = "email")})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(nullable = false)
    private LocalDateTime joinDate;


    private LocalDateTime lastLoginDate;
    private LocalDate lastLoginDateDisplay;
    private boolean isActive;
    private boolean isNotLocked;

    @Enumerated(EnumType.STRING)
    private Role role;
}

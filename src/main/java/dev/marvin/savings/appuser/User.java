package dev.marvin.savings.appuser;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_users",
        uniqueConstraints = {
        @UniqueConstraint(name = "email_id_unique", columnNames = "email"),
                @UniqueConstraint(name = "user_id_unique", columnNames = "userId")
        })
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

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
    private Role role;
}

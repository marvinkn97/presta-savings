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

    @Column(nullable = false)
    private Long joinDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_not_locked", nullable = false)
    private boolean isNotLocked;

    @Enumerated(EnumType.STRING)
    private Role role;
}

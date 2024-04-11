package dev.marvin.savings.appuser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "join_date", nullable = false)
    @CreationTimestamp
    private Long joinDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_not_locked", nullable = false)
    private boolean isNotLocked;

    @Column(name = "last_seen_on", nullable = false)
    private Long lastSeenOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

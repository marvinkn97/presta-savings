package dev.marvin.savings.appuser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_users")
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false, columnDefinition = "VARCHAR(25)")
    private String userName;

    @Column(nullable = false, columnDefinition = "VARCHAR(65)")
    private String password;

    @Column(name = "join_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime joinDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_not_locked", nullable = false)
    private boolean isNotLocked;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "role_id_fk"))
//    private Role role;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}

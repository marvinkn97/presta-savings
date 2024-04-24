package dev.marvin.savings.auth.confimationtoken;

import dev.marvin.savings.appuser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_token")
public class ConfirmationToken {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
}

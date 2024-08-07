package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.entity.SavingsAccount;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(name = "member_number_unique", columnNames = "memberNumber"))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Customer implements Serializable {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String name;

    @Column(name = "member_number", updatable = false, nullable = false)
    private String memberNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "email_verified")
    private boolean isEmailConfirmed;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "mobile_verified")
    private boolean isMobileConfirmed;

    @Column(name = "government_id", nullable = false)
    private String governmentId;

    @Column(name = "kra_pin", nullable = false)
    private String kraPin;

    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = AppUser.class)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "app_user_id_fk"))
    private AppUser appUser;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<SavingsAccount> savingsAccounts;
}

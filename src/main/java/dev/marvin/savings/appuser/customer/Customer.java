package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.savingsaccount.SavingsAccount;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(name = "member_number_unique", columnNames = "memberNumber"))
public class Customer implements Serializable {
    @Id
    @SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
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

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = AppUser.class)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "app_user_id_fk"))
    private AppUser appUser;

    @OneToMany(mappedBy = "customer")
    private List<SavingsAccount> savingsAccounts;
}

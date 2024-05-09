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

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "mobile_verified")
    private boolean isMobileConfirmed;

    @Column(name = "government_id")
    private Integer governmentId;

    @Column(name = "kra_pin")
    private String kraPin;

    @Column(name = "profile_image_id")
    private String profileImageId;

    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "app_user_id_fk"))
    private AppUser appUser;

    @OneToMany(mappedBy = "customer")
    private List<SavingsAccount> savingsAccounts;
}

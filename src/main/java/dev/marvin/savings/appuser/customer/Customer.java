package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(name = "member_number_unique", columnNames = "memberNumber"))
public class Customer extends BaseEntity implements Serializable {
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "app_user_id_fk"))
    private AppUser appUser;
}

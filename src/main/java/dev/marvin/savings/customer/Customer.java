package dev.marvin.savings.customer;

import dev.marvin.savings.appuser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_customers", uniqueConstraints = @UniqueConstraint(name = "member_number_unique", columnNames = "memberNumber"))
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

    @Column(name = "mobile_number")
    private String mobileNumber;

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

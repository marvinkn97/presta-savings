package dev.marvin.savings.customer;

import dev.marvin.savings.appuser.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_customers", uniqueConstraints = @UniqueConstraint(name = "member_number_unique", columnNames = "memberNumber"))
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "member_number", updatable = false, nullable = false)
    private String memberNumber;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "government_id")
    private Integer governmentId;

    @Column(name = "profile_image_id")
    private String profileImageId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
    private User user;
}

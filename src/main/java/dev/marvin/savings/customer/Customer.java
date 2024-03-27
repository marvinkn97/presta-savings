package dev.marvin.savings.customer;

import dev.marvin.savings.appuser.User;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_customers", uniqueConstraints = @UniqueConstraint(name = "member_number_unique", columnNames = "memberNumber"))
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "member_number", updatable = false, nullable = false)
    private String memberNumber;
    private Integer governmentId;
    private String profileImageId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
    private User user;
}

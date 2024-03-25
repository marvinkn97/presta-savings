package dev.marvin.savings.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private Long createdDate;
    private Role role;
}

package dev.marvin.savings.model;

import dev.marvin.savings.model.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private Long createdDate;
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.grantedAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
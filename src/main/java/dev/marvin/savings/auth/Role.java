package dev.marvin.savings.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static dev.marvin.savings.auth.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(Set.of(CUSTOMER_DELETE, CUSTOMER_READ)),
    CUSTOMER(Set.of(CUSTOMER_CREATE, CUSTOMER_UPDATE));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> grantedAuthorities() {

        var grantedAuthorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return grantedAuthorities;
    }
}

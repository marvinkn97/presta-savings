package dev.marvin.savings.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.marvin.savings.auth.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(Set.of(CUSTOMER_DELETE, CUSTOMER_READ)),
    CUSTOMER(Set.of(CUSTOMER_CREATE, CUSTOMER_UPDATE));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> grantedAuthorities() {

        var grantedAuthorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return grantedAuthorities;
    }
}

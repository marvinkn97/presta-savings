package dev.marvin.savings.appuser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static dev.marvin.savings.appuser.Authority.*;


@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(Set.of(USER_CREATE, USER_READ, USER_UPDATE, USER_DELETE)),
    CUSTOMER(Set.of(CUSTOMER_CREATE, CUSTOMER_UPDATE, ACCOUNT_CREATE, ACCOUNT_UPDATE, ACCOUNT_DELETE)),
    CSR(Set.of(CUSTOMER_READ, ACCOUNT_CREATE, ACCOUNT_READ, ACCOUNT_UPDATE));

    private final Set<Authority> authorities;

    public Set<SimpleGrantedAuthority> grantedAuthorities() {

        var grantedAuthorities = getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return grantedAuthorities;
    }
}

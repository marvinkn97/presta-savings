package dev.marvin.savings.model.enums;

import dev.marvin.savings.user.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static dev.marvin.savings.user.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_CUSTOMER(Set.of(CUSTOMER_CREATE, CUSTOMER_UPDATE)),
    ROLE_ADMIN(Set.of(ADMIN_CREATE, ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, CUSTOMER_READ, CUSTOMER_DELETE));


    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> grantedAuthorities(){

        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}

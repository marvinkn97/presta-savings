package dev.marvin.savings.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_READ("admin:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_DELETE("admin:delete");

    private final String permissionName;
}

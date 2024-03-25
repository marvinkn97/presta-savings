package dev.marvin.savings.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_DELETE("customer:delete"),
    ACCOUNT_CREATE("account:create"),
    ACCOUNT_READ("account:read"),
    ACCOUNT_UPDATE("account:update"),
    ACCOUNT_DELETE("account:delete"),
    TRANSACTION_PERFORM("transaction:perform"),
    TRANSACTION_READ("transaction:read");

    public final String permissionName;
}

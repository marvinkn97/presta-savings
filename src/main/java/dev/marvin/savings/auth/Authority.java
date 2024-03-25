package dev.marvin.savings.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Authority {
    CUSTOMER_CREATE,
    CUSTOMER_READ,
    CUSTOMER_UPDATE,
    CUSTOMER_DELETE,
    ACCOUNT_CREATE,
    ACCOUNT_READ,
    ACCOUNT_UPDATE,
    ACCOUNT_DELETE,
    TRANSACTION_PERFORM,
    TRANSACTION_READ;

}

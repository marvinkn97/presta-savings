package dev.marvin.savings.appuser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN,
    CUSTOMER,
    CSR
}

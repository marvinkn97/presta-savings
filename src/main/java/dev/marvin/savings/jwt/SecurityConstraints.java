package dev.marvin.savings.jwt;

import java.util.UUID;

public interface SecurityConstraints {
     String SECRET_KEY = UUID.randomUUID().toString();
}

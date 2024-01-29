package dev.marvin.savings.savingsaccount.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SavingsAccountUtil {
    public static String generateSavingsAccountNumber() {
        return "acc_" + UUID.randomUUID().toString().substring(0, 6);
    }
}

package dev.marvin.savings.savingsaccount.util;

import java.util.UUID;

public class SavingsAccountUtil {
    public static String generateSavingsAccountNumber() {
        return "presta_acc_" + UUID.randomUUID().toString().substring(0, 6);
    }
}

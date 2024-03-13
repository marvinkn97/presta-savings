package dev.marvin.savings.util;

import dev.marvin.savings.model.customer.Customer;
import dev.marvin.savings.model.savingsaccount.SavingsAccount;
import dev.marvin.savings.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class UniqueIDSupplier<T> implements Supplier<String> {
    private final Class<T> type;

    @Override
    public String get() {
        String uniqueID = null;
        if (type == Customer.class) {
            uniqueID = "MEM";
        } else if (type == SavingsAccount.class) {
            uniqueID = "ACC";
        } else if (type == Transaction.class) {
            uniqueID = "TX";
        }
        return uniqueID + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}

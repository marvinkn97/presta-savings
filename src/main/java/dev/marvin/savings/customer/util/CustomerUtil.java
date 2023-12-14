package dev.marvin.savings.customer.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerUtil {
    public static String generateCustomerMemberNumber(){
        return "presta" + UUID.randomUUID().toString().substring(0,6);
    }
}

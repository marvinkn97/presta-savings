package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}

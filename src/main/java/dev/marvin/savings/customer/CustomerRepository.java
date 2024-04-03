package dev.marvin.savings.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> saveCustomer(Customer customer);
    Optional<Customer> findByMemberNumber(String memberNumber);
}

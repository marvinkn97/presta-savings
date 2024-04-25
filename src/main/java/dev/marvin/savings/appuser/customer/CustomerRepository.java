package dev.marvin.savings.appuser.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT c FROM Customer c WHERE c.memberNumber = :memNo")
    Optional<Customer> findByMemberNumber(@Param(value = "memNo") String memberNumber);

    boolean existsByEmail(String email);
}

package dev.marvin.savings.savingsaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Integer> {
    @Query(value = "SELECT s FROM SavingsAccount s WHERE s.accountNumber = :accNo")
    Optional<SavingsAccount> findByAccountNumber(@Param(value = "accNo") String accountNumber);

    @Query(value = "SELECT s FROM SavingsAccount s WHERE s.customer.memberNumber = :memberNumber ")
    List<SavingsAccount> findByCustomerMemberNumber(@Param(value = "memberNumber") String memberNumber);
}

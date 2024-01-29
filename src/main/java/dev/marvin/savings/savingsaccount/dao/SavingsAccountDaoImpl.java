package dev.marvin.savings.savingsaccount.dao;

import dev.marvin.savings.savingsaccount.model.SavingsAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Primary
@Repository
public class SavingsAccountDaoImpl implements SavingsAccountDao{
    private final JdbcTemplate jdbcTemplate;

    public SavingsAccountDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertAccount(SavingsAccount savingsAccount) {
        final String sql = """
                INSERT INTO t_savings_account(account_number, account_name, account_type, balance, created_date, member_no)
                VALUES(?, ?, ?, ?, ?, ?)
                """;
        int rowsAffected = jdbcTemplate.update(sql, savingsAccount.getAccountNumber(), savingsAccount.getAccountName(), savingsAccount.getSavingsAccountType().name(), savingsAccount.getBalance(), savingsAccount.getCreatedDate(), savingsAccount.getCustomer().getMemberNumber());
        log.info("SAVINGS ACCOUNT INSERT RESULT = " + rowsAffected);
    }

    @Override
    public List<SavingsAccount> getAllAccounts() {
        return null;
    }

    @Override
    public List<SavingsAccount> getAccountsByMemberNumber(String memberNumber) {
        return null;
    }

    @Override
    public void updateAccount(SavingsAccount savingsAccount) {

    }

    @Override
    public void deleteAccount(SavingsAccount savingsAccount) {

    }
}

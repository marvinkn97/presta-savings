package dev.marvin.savings.dao;

import dev.marvin.savings.dao.rowmapper.SavingsAccountRowMapper;
import dev.marvin.savings.model.SavingsAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Primary
@Repository
public class SavingsAccountDaoImpl implements SavingsAccountDao {
    private final JdbcTemplate jdbcTemplate;
    private final SavingsAccountRowMapper savingsAccountRowMapper;

    public SavingsAccountDaoImpl(JdbcTemplate jdbcTemplate, SavingsAccountRowMapper savingsAccountRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.savingsAccountRowMapper = savingsAccountRowMapper;
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
        final String sql = """
                SELECT account_number, account_name, account_type, balance, created_date, member_no FROM t_savings_account
                """;
        return jdbcTemplate.query(sql, savingsAccountRowMapper);
    }

    @Override
    public List<SavingsAccount> getAccountsByMemberNumber(String memberNumber) {
        final String sql = """
                 SELECT account_number, account_name, account_type, balance, created_date, member_no FROM t_savings_account WHERE member_no = ?
                """;
        return jdbcTemplate.query(sql, savingsAccountRowMapper, memberNumber);
    }

    @Override
    public List<SavingsAccount> getAccountsByAccountType(String accountType) {
        final String sql = """
                 SELECT account_number, account_name, account_type, balance, created_date, member_no FROM t_savings_account WHERE account_type = ?
                """;

        List<SavingsAccount> s = jdbcTemplate.query(sql, savingsAccountRowMapper, accountType);
        System.out.println(s);
        return s;
    }

    @Override
    public SavingsAccount getAccountByAccountNumber(String accountNumber) {
        final String sql = """
                 SELECT account_number, account_name, account_type, balance, created_date, member_no FROM t_savings_account WHERE account_number = ?
                """;
        return jdbcTemplate.queryForObject(sql, savingsAccountRowMapper, accountNumber);
    }

    @Override
    public void updateAccount(SavingsAccount savingsAccount) {
        if (savingsAccount.getAccountName() != null) {
            final String sql = """
                    UPDATE t_savings_account
                    SET account_name = ?
                    WHERE account_number = ?
                    """;
            int rowsAffected = jdbcTemplate.update(sql, savingsAccount.getAccountName(), savingsAccount.getAccountNumber());
            log.info("SAVINGS ACCOUNT NAME UPDATE RESULT = " + rowsAffected);
        }
    }

    @Override
    public void deleteAccount(SavingsAccount savingsAccount) {
        final String sql = """
                DELETE FROM t_savings_account WHERE account_number = ?
                """;
        int rowsAffected = jdbcTemplate.update(sql, savingsAccount.getAccountNumber());
        log.info("SAVINGS ACCOUNT DELETE RESULT = " + rowsAffected);

    }

}

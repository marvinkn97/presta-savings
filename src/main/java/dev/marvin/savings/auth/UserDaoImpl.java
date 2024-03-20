package dev.marvin.savings.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Boolean insertUser(User user) {
        String sql = "INSERT INTO users (name, email, password, created_date, role) VALUES (:name, :email, :password, :createdDate, :role)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());
        params.addValue("password", user.getPassword());
        params.addValue("createdDate", user.getCreatedDate());
        params.addValue("role", user.getRole().name());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

        return rowsAffected > 0;
    }
}

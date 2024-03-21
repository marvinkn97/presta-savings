package dev.marvin.savings.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDaoImpl implements UserDao{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String EMAIL_PARAM = "email";

    @Override
    public Optional<Integer> insertUser(User user) {
        String sql = "INSERT INTO users (email, password, created_date, role) VALUES (:email, :password, :createdDate, :role)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(EMAIL_PARAM, user.getEmail());
        params.addValue("password", user.getPassword());
        params.addValue("createdDate", user.getCreatedDate());
        params.addValue("role", user.getRole().name());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params, keyHolder);
        log.info("USER INSERT RESULT = " + rowsAffected);

        return Optional.ofNullable(keyHolder.getKey().intValue());

    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * from t_users WHERE email = :email";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(EMAIL_PARAM, email);

        List<User> users =namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setEmail(rs.getString(EMAIL_PARAM));
            user.setPassword(rs.getString("password"));
            user.setRole(Role.valueOf(rs.getString("role")));
            user.setCreatedDate(rs.getLong("created_date"));
            return user;
        });

        return users.stream().findFirst();
    }
}

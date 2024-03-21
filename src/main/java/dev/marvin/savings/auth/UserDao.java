package dev.marvin.savings.auth;

import java.util.Optional;

public interface UserDao {
   Optional<Integer> insertUser(User user);
   Optional<User> getUserByEmail(String email);
}

package dev.marvin.savings.auth;

import dev.marvin.savings.appuser.User;

import java.util.Optional;

public interface UserDao{
   Optional<Integer> insertUser(User user);
   Optional<User> getUserByEmail(String email);
   //   void deleteUser(String email);

//   void updateUser(UserDetails user);
//   void changePassword(String oldPassword, String newPassword);
//   boolean userExists(String email);
}

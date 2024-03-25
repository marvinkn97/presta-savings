package dev.marvin.savings.auth;

import java.util.Optional;

public interface UserDao{
   Optional<Integer> insertUser(User user);
   Optional<User> getUserByEmail(String email);
   //   void deleteUser(String email);

//   void createUser(UserDetails user);
//   void updateUser(UserDetails user);
//   void changePassword(String oldPassword, String newPassword);
//   boolean userExists(String email);
}

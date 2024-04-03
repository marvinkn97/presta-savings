package dev.marvin.savings.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsUserWithEmail(String email);
    Optional<User> saveUser(User user);

//   void changePassword(String oldPassword, String newPassword);
//   boolean userExists(String email);
}

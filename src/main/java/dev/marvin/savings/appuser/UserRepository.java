package dev.marvin.savings.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u FROM User u WHERE u.userName = :username")
    Optional<User> findByUsername(@Param(value = "username") String username);
    boolean existsByUserName(String userName);

//   void changePassword(String oldPassword, String newPassword);
//   boolean userExists(String email);
}

package dev.marvin.savings.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    @Query(value = "SELECT u FROM AppUser u WHERE u.username = :username")
    Optional<AppUser> findByUsername(@Param(value = "username") String username);

    boolean existsByUsername(String username);

//   void changePassword(String oldPassword, String newPassword);
}

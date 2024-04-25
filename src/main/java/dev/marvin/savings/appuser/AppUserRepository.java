package dev.marvin.savings.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    @Query(value = "SELECT u FROM AppUser u WHERE u.username = :username")
    Optional<AppUser> findByUsername(@Param(value = "username") String username);

    boolean existsByUsername(String username);

    @Query(value = "UPDATE AppUser u SET u.isEnabled = :enabled WHERE u.username = :username")
    @Modifying
    void setAppUserToEnabled(@Param(value = "enabled") Boolean enabled, @Param(value = "username") String username);

//   void changePassword(String oldPassword, String newPassword);
//   boolean userExists(String email);
}

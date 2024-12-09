package dev.marvin.savings.repository;

import dev.marvin.savings.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.mobileNumber = :mobile")
    Optional<UserEntity> findByMobile(@Param("mobile") String mobile);
}
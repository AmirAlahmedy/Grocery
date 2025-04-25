package com.grocery.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.user_id = ?1")
    Optional<User> findByUserId(String userId);
}

package com.hungthinh.socalnetwork_hungthinh.repository;

import com.hungthinh.socalnetwork_hungthinh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
}

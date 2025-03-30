package com.hungthinh.socalnetwork_hungthinh.repository;

import com.hungthinh.socalnetwork_hungthinh.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String roleAdmin);
}

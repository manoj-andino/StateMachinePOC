package com.andinolabs.demo.stateengine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    Optional<UserRole> findByName(com.andinolabs.demo.commons.UserRole name);

}

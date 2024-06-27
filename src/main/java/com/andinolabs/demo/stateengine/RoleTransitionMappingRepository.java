package com.andinolabs.demo.stateengine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleTransitionMappingRepository extends JpaRepository<RoleTransitionMapping, UUID> {

    List<RoleTransitionMapping> findByRole(UserRole role);

}

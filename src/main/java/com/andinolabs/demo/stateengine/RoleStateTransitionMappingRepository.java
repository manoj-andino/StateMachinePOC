package com.andinolabs.demo.stateengine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleStateTransitionMappingRepository extends JpaRepository<RoleStateTransitionMapping, UUID> {

    List<RoleStateTransitionMapping> findByRole(UserRole role);

}

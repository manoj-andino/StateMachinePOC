package com.andinolabs.demo.stateengine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntityRepository extends JpaRepository<Entity, UUID> {

    Optional<Entity> findByName(String name);

}

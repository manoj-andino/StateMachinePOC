package com.andinolabs.demo.stateengine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EntityStateTransitionRepository extends JpaRepository<EntityStateTransition, UUID> {

    List<EntityStateTransition> findByEntityAndFromStateAndToState(Entity entity, String fromState, String toState);

}

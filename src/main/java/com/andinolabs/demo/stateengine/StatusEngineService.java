package com.andinolabs.demo.stateengine;

import com.andinolabs.demo.commons.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusEngineService {

    private final EntityRepository entityRepository;

    private final UserRoleRepository userRoleRepository;

    private final EntityStateTransitionRepository entityStateTransitionRepository;

    private final RoleStateTransitionMappingRepository roleStateTransitionMappingRepository;

    public StatusEngineService(EntityRepository entityRepository, UserRoleRepository userRoleRepository, EntityStateTransitionRepository entityStateTransitionRepository, RoleStateTransitionMappingRepository roleStateTransitionMappingRepository) {
        this.entityRepository = entityRepository;
        this.userRoleRepository = userRoleRepository;
        this.entityStateTransitionRepository = entityStateTransitionRepository;
        this.roleStateTransitionMappingRepository = roleStateTransitionMappingRepository;
    }

    public Transitionable changeStateFor(UserAction userAction) {
        var entityName = userAction.entity().getClass().getSimpleName().toUpperCase();
        var entity = entityRepository.findByName(entityName)
                .orElseThrow(() -> new StateTransitionException("Entity %s not found".formatted(entityName)));
        var validTransitions = entityStateTransitionRepository
                .findByEntityAndFromStateAndToState(entity, userAction.entity().getState(), userAction.toStatus());
        if (CollectionUtils.isEmpty(validTransitions)) {
            throw new StateTransitionException("Current state is %s, invalid transition to %s"
                    .formatted(userAction.entity().getState(), userAction.toStatus()));
        }
        if (isTransitionAllowedForRole(validTransitions, userAction.role())) {
            throw new StateTransitionException("Role not allowed to perform this transition");
        }
        userAction.entity().updateState(userAction.toStatus());
        return userAction.entity();
    }

    private boolean isTransitionAllowedForRole(List<EntityStateTransition> possibleTransitions, UserRole userRole) {
        var possibleTransitionIds = possibleTransitions.stream()
                .map(EntityStateTransition::getId)
                .collect(Collectors.toSet());
        var role = userRoleRepository.findByName(userRole)
                .orElseThrow(() -> new StateTransitionException("Role not found"));
        var roleTransitionMappings = roleStateTransitionMappingRepository.findByRole(role);
        return roleTransitionMappings.stream()
                .map(RoleStateTransitionMapping::getStateTransition)
                .map(EntityStateTransition::getId)
                .noneMatch(possibleTransitionIds::contains);
    }

}

package com.andinolabs.demo.stateengine;

import com.andinolabs.demo.commons.StateTransitionException;
import com.andinolabs.demo.commons.StatusTransitionDraft;
import com.andinolabs.demo.commons.StatusTransitionRepresentation;
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

    private final RoleTransitionMappingRepository roleTransitionMappingRepository;

    public StatusEngineService(EntityRepository entityRepository, UserRoleRepository userRoleRepository, EntityStateTransitionRepository entityStateTransitionRepository, RoleTransitionMappingRepository roleTransitionMappingRepository) {
        this.entityRepository = entityRepository;
        this.userRoleRepository = userRoleRepository;
        this.entityStateTransitionRepository = entityStateTransitionRepository;
        this.roleTransitionMappingRepository = roleTransitionMappingRepository;
    }

    public StatusTransitionRepresentation verifyFor(StatusTransitionDraft statusTransitionDraft) {
        var entityName = statusTransitionDraft.entity().name();
        var entity = entityRepository.findByName(entityName);
        if (entity.isEmpty()) {
            return new StatusTransitionRepresentation.ForbiddenTransition("Entity %s not found".formatted(entityName));
        }
        var validTransitions = entityStateTransitionRepository
                .findByEntityAndFromStateAndToState(entity.get(), statusTransitionDraft.entity().currentStatus(), statusTransitionDraft.toStatus());
        if (CollectionUtils.isEmpty(validTransitions)) {
            return new StatusTransitionRepresentation.ForbiddenTransition("Current state is %s, invalid transition to %s"
                    .formatted(statusTransitionDraft.entity().currentStatus(), statusTransitionDraft.toStatus()));
        }
        if (isTransitionAllowedForRole(validTransitions, statusTransitionDraft.role())) {
            return new StatusTransitionRepresentation.ForbiddenTransition("Role not allowed to perform this transition");
        }
        return new StatusTransitionRepresentation.AllowedTransition();
    }

    private boolean isTransitionAllowedForRole(List<EntityStateTransition> possibleTransitions, UserRole userRole) {
        var possibleTransitionIds = possibleTransitions.stream()
                .map(EntityStateTransition::getId)
                .collect(Collectors.toSet());
        var role = userRoleRepository.findByName(userRole)
                .orElseThrow(() -> new StateTransitionException("Role not found"));
        var roleTransitionMappings = roleTransitionMappingRepository.findByRole(role);
        return roleTransitionMappings.stream()
                .map(RoleTransitionMapping::getStateTransition)
                .map(EntityStateTransition::getId)
                .noneMatch(possibleTransitionIds::contains);
    }

}

package com.andinolabs.demo.landprocurement;

import com.andinolabs.demo.commons.EntityDraft;
import com.andinolabs.demo.commons.StateMachineClient;
import com.andinolabs.demo.commons.StateTransitionException;
import com.andinolabs.demo.commons.StatusTransitionDraft;
import com.andinolabs.demo.commons.StatusTransitionRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LandProcurementService {

    private final LandRepository landRepository;

    public LandProcurementService(LandRepository landRepository) {
        this.landRepository = landRepository;
    }

    public Land changeStateFor(UserLandProcurementAction userLandProcurementAction) {
        Land land = landRepository.findById(userLandProcurementAction.landId())
                .orElseThrow(() -> new IllegalArgumentException("Land not found"));
        switch (isStateChangeAllowed(userLandProcurementAction, land)) {
            case StatusTransitionRepresentation.AllowedTransition ignored -> {
                log.info("State transition allowed");
                land.setStatus(userLandProcurementAction.toStatus());
            }
            case StatusTransitionRepresentation.ForbiddenTransition forbiddenTransition -> {
                log.error("Forbidden state transition");
                throw new StateTransitionException(forbiddenTransition.reason());
            }
        }
        log.info("Saving land {}", land);
        return landRepository.save(land);
    }

    public StatusTransitionRepresentation isStateChangeAllowed(UserLandProcurementAction userLandProcurementAction, Land land) {
        var entityDraft = new EntityDraft(land.getId(), "LAND", land.getStatus().name());
        var statusTransitionDraft = new StatusTransitionDraft(userLandProcurementAction.role(), entityDraft, userLandProcurementAction.toStatus().name());
        return StateMachineClient.isStateChangeAllowed(statusTransitionDraft);
    }

}

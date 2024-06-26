package com.andinolabs.demo.stateengine;

import com.andinolabs.demo.commons.UserRole;
import com.andinolabs.demo.landprocurement.Land;
import com.andinolabs.demo.landprocurement.UserLandProcurementAction;

public record UserAction(UserRole role, Transitionable entity, String toStatus) {

    public UserAction(UserLandProcurementAction userLandProcurementAction, Land land) {
        this(userLandProcurementAction.role(), land, userLandProcurementAction.toStatus().name());
    }

}

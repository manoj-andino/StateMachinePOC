package com.andinolabs.demo.landprocurement;

import com.andinolabs.demo.commons.UserRole;

import java.util.UUID;

public record UserLandProcurementAction(UserRole role, UUID landId, LandProcurementStatus toStatus) {
}
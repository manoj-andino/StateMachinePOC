package com.andinolabs.demo.apartmentsale;

import com.andinolabs.demo.commons.UserRole;

import java.util.UUID;

public record UserApartmentSaleAction(UserRole role, UUID apartmentId, ApartmentSaleStatus toStatus) {
}

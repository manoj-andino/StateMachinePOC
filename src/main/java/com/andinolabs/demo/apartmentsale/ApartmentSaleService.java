package com.andinolabs.demo.apartmentsale;

import com.andinolabs.demo.commons.EntityDraft;
import com.andinolabs.demo.commons.StateMachineClient;
import com.andinolabs.demo.commons.StateTransitionException;
import com.andinolabs.demo.commons.StatusTransitionDraft;
import com.andinolabs.demo.commons.StatusTransitionRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApartmentSaleService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentSaleService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Apartment changeStateFor(UserApartmentSaleAction userApartmentSaleAction) {
        Apartment apartment = apartmentRepository.findById(userApartmentSaleAction.apartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Apartment not found"));
        switch (isStateChangeAllowed(userApartmentSaleAction, apartment)) {
            case StatusTransitionRepresentation.AllowedTransition ignored -> {
                log.info("State transition allowed");
                apartment.setStatus(userApartmentSaleAction.toStatus());
            }
            case StatusTransitionRepresentation.ForbiddenTransition forbiddenTransition -> {
                log.error("Forbidden state transition");
                throw new StateTransitionException(forbiddenTransition.reason());
            }
        }
        return apartmentRepository.save(apartment);
    }

    public StatusTransitionRepresentation isStateChangeAllowed(UserApartmentSaleAction userApartmentSaleAction, Apartment apartment) {
        var entityDraft = new EntityDraft(apartment.getId(), "APARTMENT", apartment.getStatus().name());
        var statusTransitionDraft = new StatusTransitionDraft(userApartmentSaleAction.role(), entityDraft, userApartmentSaleAction.toStatus().name());
        return StateMachineClient.isStateChangeAllowed(statusTransitionDraft);
    }

}

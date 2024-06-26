package com.andinolabs.demo.apartmentsale;

import com.andinolabs.demo.landprocurement.Land;
import com.andinolabs.demo.landprocurement.UserLandProcurementAction;
import com.andinolabs.demo.stateengine.StatusEngineService;
import com.andinolabs.demo.stateengine.UserAction;
import org.springframework.stereotype.Service;

@Service
public class ApartmentSaleService {

    private final ApartmentRepository apartmentRepository;

    private final StatusEngineService statusEngineService;

    public ApartmentSaleService(ApartmentRepository apartmentRepository, StatusEngineService statusEngineService) {
        this.apartmentRepository = apartmentRepository;
        this.statusEngineService = statusEngineService;
    }

    public Apartment changeStateFor(UserApartmentSaleAction userApartmentSaleAction) {
        Apartment apartment = apartmentRepository.findById(userApartmentSaleAction.apartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Apartment not found"));
        UserAction userAction = new UserAction(userApartmentSaleAction, apartment);
        Apartment updatedLand = (Apartment) statusEngineService.changeStateFor(userAction);
        return apartmentRepository.save(updatedLand);
    }
}

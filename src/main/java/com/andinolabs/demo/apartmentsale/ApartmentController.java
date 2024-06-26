package com.andinolabs.demo.apartmentsale;

import com.andinolabs.demo.landprocurement.Land;
import com.andinolabs.demo.landprocurement.UserLandProcurementAction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApartmentController {

    private final ApartmentSaleService apartmentSaleService;

    public ApartmentController(ApartmentSaleService apartmentSaleService) {
        this.apartmentSaleService = apartmentSaleService;
    }

    @PostMapping("/apartment/change-state")
    public ResponseEntity<Apartment> changeStateFor(@RequestBody UserApartmentSaleAction action) {
        Apartment apartment = apartmentSaleService.changeStateFor(action);
        return ResponseEntity.ok().body(apartment);
    }

}

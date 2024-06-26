package com.andinolabs.demo.landprocurement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandController {

    private final LandProcurementService landProcurementService;

    public LandController(LandProcurementService landProcurementService) {
        this.landProcurementService = landProcurementService;
    }

    @PostMapping("/land/change-state")
    public ResponseEntity<Land> changeStateFor(@RequestBody UserLandProcurementAction action) {
        Land land = landProcurementService.changeStateFor(action);
        return ResponseEntity.ok().body(land);
    }

}

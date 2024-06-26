package com.andinolabs.demo.landprocurement;

import com.andinolabs.demo.stateengine.StatusEngineService;
import com.andinolabs.demo.stateengine.UserAction;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LandProcurementService {

    private final LandRepository landRepository;

    private final StatusEngineService statusEngineService;

    public LandProcurementService(LandRepository landRepository, StatusEngineService statusEngineService) {
        this.landRepository = landRepository;
        this.statusEngineService = statusEngineService;
    }

    public Land changeStateFor(UserLandProcurementAction userLandProcurementAction) {
        Land land = landRepository.findById(userLandProcurementAction.landId())
                .orElseThrow(() -> new IllegalArgumentException("Land not found"));
        UserAction userAction = new UserAction(userLandProcurementAction, land);
        Land updatedLand = (Land) statusEngineService.changeStateFor(userAction);
        return landRepository.save(updatedLand);
    }

}

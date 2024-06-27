package com.andinolabs.demo.stateengine;

import com.andinolabs.demo.commons.StatusTransitionDraft;
import com.andinolabs.demo.commons.StatusTransitionRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusEngineController {

    private final StatusEngineService statusEngineService;

    public StatusEngineController(StatusEngineService statusEngineService) {
        this.statusEngineService = statusEngineService;
    }

    @PostMapping("/verify-state-transition")
    public ResponseEntity<String> verifyFor(@RequestBody StatusTransitionDraft statusTransitionDraft) {
        var statusTransitionRepresentation = statusEngineService.verifyFor(statusTransitionDraft);
        return switch (statusTransitionRepresentation) {
            case StatusTransitionRepresentation.AllowedTransition ignored -> ResponseEntity.ok().build();
            case StatusTransitionRepresentation.ForbiddenTransition forbiddenTransition ->
                    ResponseEntity.unprocessableEntity().body(forbiddenTransition.reason());
        };
    }

}

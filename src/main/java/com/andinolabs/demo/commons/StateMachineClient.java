package com.andinolabs.demo.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.io.InputStream;

@Slf4j
public class StateMachineClient {

    public static StatusTransitionRepresentation isStateChangeAllowed(StatusTransitionDraft statusTransitionDraft) {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:8080//verify-state-transition")
                .defaultHeader("Content-Type", "application/json")
                .build();
        try {
            ResponseEntity<String> stringResponseEntity = restClient.post().contentType(MediaType.APPLICATION_JSON).body(statusTransitionDraft).retrieve()
                    .onStatus(httpStatusCode -> httpStatusCode == HttpStatus.UNPROCESSABLE_ENTITY, (request, response) -> {
                        InputStream inputStream = response.getBody();
                        String responseBody = new String(inputStream.readAllBytes());
                        throw new StateTransitionException(responseBody);
                    }).toEntity(String.class);
            log.info("Response status code from verify-state-transition: {}", stringResponseEntity.getStatusCode());
            log.info("Response from verify-state-transition: {}", stringResponseEntity.getBody());
        } catch (StateTransitionException e) {
            log.error("Forbidden state transition", e);
            return new StatusTransitionRepresentation.ForbiddenTransition(e.getMessage());
        } catch (Exception e) {
            log.error("Unknown error while verifying state transition", e);
            return new StatusTransitionRepresentation.ForbiddenTransition("Unknown error while verifying state transition");
        }
        log.info("State transition allowed");
        return new StatusTransitionRepresentation.AllowedTransition();
    }

}

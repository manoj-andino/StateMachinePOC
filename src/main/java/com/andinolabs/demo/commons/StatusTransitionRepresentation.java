package com.andinolabs.demo.commons;

public sealed interface StatusTransitionRepresentation {

    record AllowedTransition() implements StatusTransitionRepresentation {
    }

    record ForbiddenTransition(String reason) implements StatusTransitionRepresentation {
    }

}

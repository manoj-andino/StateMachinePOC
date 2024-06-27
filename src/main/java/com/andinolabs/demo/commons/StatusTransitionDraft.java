package com.andinolabs.demo.commons;

public record StatusTransitionDraft(UserRole role, EntityDraft entity, String toStatus) {
}

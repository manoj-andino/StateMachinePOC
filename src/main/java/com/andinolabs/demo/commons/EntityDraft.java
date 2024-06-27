package com.andinolabs.demo.commons;

import java.util.UUID;

public record EntityDraft(UUID id, String name, String currentStatus) {
}

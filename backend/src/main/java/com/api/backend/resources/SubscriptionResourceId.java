package com.api.backend.resources;

import com.api.backend.models.Subscription;

import java.util.UUID;

public record SubscriptionResourceId(
        UUID id,
        String name,
        Integer numberSession,
        Integer duration
) {
    public SubscriptionResourceId(Subscription subscription){
        this(
                subscription.getId(),
                subscription.getName(),
                subscription.getNumberSessions(),
                subscription.getDuration()
        );

    }
}

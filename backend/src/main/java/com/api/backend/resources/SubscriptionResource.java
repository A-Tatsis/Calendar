package com.api.backend.resources;

import com.api.backend.models.Subscription;
import com.api.backend.models.repositories.SubscriptionRepository;

public record SubscriptionResource(
        String name,
        Integer numberSession,
        Integer duration
) {
    public SubscriptionResource(Subscription subscription){
        this(
                subscription.getName(),
                subscription.getNumberSessions(),
                subscription.getDuration()
        );

    }
}

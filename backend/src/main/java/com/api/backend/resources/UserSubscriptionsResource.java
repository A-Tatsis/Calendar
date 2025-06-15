package com.api.backend.resources;

import com.api.backend.models.UserSubscriptions;

import java.time.LocalDate;
import java.util.UUID;

public record UserSubscriptionsResource(
        UUID subscription,
        LocalDate registeredDate,
        LocalDate expiredDate,
        UUID idUser,
        Integer status
) {
    public UserSubscriptionsResource(UserSubscriptions userSubscriptions) {
        this(
                userSubscriptions.getSubscription(),
                userSubscriptions.getRegisteredDate(),
                userSubscriptions.getExpiredDate(),
                userSubscriptions.getIdUser(),
                userSubscriptions.getStatus()
        );
    }
}

package com.api.backend.service;

import com.api.backend.config.SecurityUtils;
import com.api.backend.models.Subscription;
import com.api.backend.models.UserSubscriptions;
import com.api.backend.models.repositories.UserSubscriptionsRepository;
import com.api.backend.resources.UserSubscriptionsResource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserSubscriptionsService {

    private final SubscriptionService subscriptionService;

    private final UserSubscriptionsRepository userSubscriptionsRepository;

    public UserSubscriptionsService(SubscriptionService subscriptionService, UserSubscriptionsRepository userSubscriptionsRepository) {
        this.userSubscriptionsRepository = userSubscriptionsRepository;
        this.subscriptionService = subscriptionService;
    }

    public List<UserSubscriptions> findAll() {
        return userSubscriptionsRepository.findAll();
    }

    public UserSubscriptions findById(UUID id) {
        return userSubscriptionsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User subscriptions not found with the id " + id)
        );
    }

    public UserSubscriptions createUserSubscriptions(UserSubscriptionsResource request) {
        UserSubscriptions userSubscriptions = new UserSubscriptions();
        userSubscriptions.setSubscription(request.subscription());
        userSubscriptions.setRegisteredDate(request.registeredDate());
        Subscription subscription = subscriptionService.findById(request.subscription());
        LocalDate expirationDate = request.registeredDate().plusDays(subscription.getDuration());
        userSubscriptions.setExpiredDate(expirationDate);
        userSubscriptions.setIdUser(SecurityUtils.getCurrentUserId());
        userSubscriptions.setStatus(request.status());
        return userSubscriptionsRepository.save(userSubscriptions);
    }

    public void deleteUserSubscription(UUID id) {
        if (!userSubscriptionsRepository.existsById(id)) {
            throw new EntityNotFoundException("The subscription of the user id " + id + " does not exist");
        }
        userSubscriptionsRepository.deleteById(id);
    }

    public UserSubscriptions updateUserSubscription(UUID id, UserSubscriptionsResource request) {
        UserSubscriptions userSubscriptions = userSubscriptionsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("The subscription of the user id " + id + " does not exist")
        );

        if (request.subscription() != null) {
            userSubscriptions.setSubscription(request.subscription());
        }

        if (request.idUser() != null) {
            userSubscriptions.setIdUser(request.idUser());
        }

        if (request.status() != null) {
            userSubscriptions.setStatus(request.status());
        }

        return userSubscriptionsRepository.save(userSubscriptions);
    }

}

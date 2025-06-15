package com.api.backend.service;

import com.api.backend.models.Subscription;
import com.api.backend.models.repositories.SubscriptionRepository;
import com.api.backend.resources.SubscriptionResource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    public Subscription createSubscription(SubscriptionResource request) {
        Subscription subscription = new Subscription();
        subscription.setName(request.name());
        subscription.setNumberSessions(request.numberSession());
        subscription.setDuration(request.duration());
        return subscriptionRepository.save(subscription);
    }

    public Subscription findById(UUID id) {
        return subscriptionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Subscription not found with the id " + id)
        );
    }


    public void deleteSubscription(UUID id){
        if (!subscriptionRepository.existsById(id)) {
            throw new EntityNotFoundException("Subscription not found with the id " + id);
        }
        subscriptionRepository.deleteById(id);
    }

    public Subscription updateSubscription(UUID id, SubscriptionResource request) {

        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Subscription not found with the id " + id)
        );

        if (request.name() != null) {
            subscription.setName(request.name());
        }

        if (request.numberSession() != null) {
            subscription.setNumberSessions(request.numberSession());
        }

        if (request.duration() != null) {
            subscription.setDuration(request.duration());
        }

        return subscriptionRepository.save(subscription);
    }
}

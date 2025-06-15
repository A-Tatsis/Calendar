package com.api.backend.models.repositories;

import com.api.backend.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription,UUID> {
}

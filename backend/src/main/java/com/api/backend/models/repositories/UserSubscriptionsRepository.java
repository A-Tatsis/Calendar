package com.api.backend.models.repositories;

import com.api.backend.models.UserSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSubscriptionsRepository extends JpaRepository<UserSubscriptions, UUID> {

    Optional<UserSubscriptions> findTopByIdUserAndStatusOrderByRegisteredDateDesc(UUID idUser, Integer status);

}

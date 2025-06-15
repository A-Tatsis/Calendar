package com.api.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table( name = "userSubscriptions")
public class UserSubscriptions {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "subscription")
    private UUID subscription;

    @Column(name = "registered")
    private LocalDate registeredDate;

    @Column(name = "expired")
    private LocalDate expiredDate;

    @Column(name = "idUser")
    private UUID idUser;

    @Column(name = "status")
    private Integer status;

    public UserSubscriptions() {}

    public UserSubscriptions(UUID subscription, LocalDate registeredDate, LocalDate expiredDate, UUID idUser, Integer status) {
        this.subscription = subscription;
        this.registeredDate = registeredDate;
        this.expiredDate = expiredDate;
        this.idUser = idUser;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSubscription() {
        return subscription;
    }

    public void setSubscription(UUID subscription) {
        this.subscription = subscription;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

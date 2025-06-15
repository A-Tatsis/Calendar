package com.api.backend.models;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "closedAppointment")
public class ClosedAppointment {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "idAppointment")
    private UUID idAppointment;

    @Column(name = "idUser")
    private UUID idUser;

    @Column(name = "status")
    private Integer status;

    @Column(name = "dateRegistered")
    private LocalDateTime registeredDate;

    public ClosedAppointment() {}

    public ClosedAppointment(UUID idAppointment, UUID idUser, Integer status, LocalDateTime registeredDate) {
        this.idAppointment = idAppointment;
        this.idUser = idUser;
        this.status = status;
        this.registeredDate = registeredDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(UUID idAppointment) {
        this.idAppointment = idAppointment;
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

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }
}

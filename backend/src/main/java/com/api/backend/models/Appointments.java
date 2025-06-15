package com.api.backend.models;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class Appointments {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String nameSession;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "numberOfUsers")
    private Integer number;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "status")
    private Integer status;

    @Column(name = "userId")
    private UUID userId;

    @Column(name = "numberOfWaitingUsers")
    private Integer waitingNumber;

    public  Appointments() {};

    public Appointments(String nameSession, LocalDate date, Integer number, String teacher, Integer status, UUID userId, Integer waitingNumber) {
        this.nameSession = nameSession;
        this.date = date;
        this.number = number;
        this.teacher = teacher;
        this.status = status;
        this.userId = userId;
        this.waitingNumber = waitingNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNameSession() {
        return nameSession;
    }

    public void setNameSession(String nameSession) {
        this.nameSession = nameSession;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getWaitingNumber() {
        return waitingNumber;
    }

    public void setWaitingNumber(Integer waitingNumber) {
        this.waitingNumber = waitingNumber;
    }

}

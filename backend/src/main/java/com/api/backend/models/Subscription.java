package com.api.backend.models;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "numberSessions")
    private Integer numberSessions;

    @Column(name = "duration")
    private Integer duration;

    public Subscription() {}

    public Subscription(String name, Integer numberSessions, Integer duration) {
        this.name = name;
        this.numberSessions = numberSessions;
        this.duration = duration;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberSessions() {
        return numberSessions;
    }

    public void setNumberSessions(Integer numberSessions) {
        this.numberSessions = numberSessions;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}

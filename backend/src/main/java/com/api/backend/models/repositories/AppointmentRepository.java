package com.api.backend.models.repositories;

import com.api.backend.models.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointments, UUID> {

}

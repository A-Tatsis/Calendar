package com.api.backend.resources;

import com.api.backend.models.ClosedAppointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ClosedAppointmentResourceId(
        UUID id,
        UUID idAppointment,
        UUID idUser,
        Integer status,
        LocalDateTime registeredDate
) {
    public ClosedAppointmentResourceId(ClosedAppointment closedAppointment) {
        this(
                closedAppointment.getId(),
                closedAppointment.getIdAppointment(),
                closedAppointment.getIdUser(),
                closedAppointment.getStatus(),
                closedAppointment.getRegisteredDate()
        );
    }
}

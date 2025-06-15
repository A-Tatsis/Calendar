package com.api.backend.resources;

import com.api.backend.models.ClosedAppointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ClosedAppointmentResource(
        UUID idAppointment,
        UUID idUser,
        Integer status,
        LocalDateTime registeredDate
) {
    public ClosedAppointmentResource(ClosedAppointment closedAppointment) {
        this(
                closedAppointment.getIdAppointment(),
                closedAppointment.getIdUser(),
                closedAppointment.getStatus(),
                closedAppointment.getRegisteredDate()
        );
    }
}

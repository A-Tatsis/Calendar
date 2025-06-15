package com.api.backend.resources;

import com.api.backend.models.Appointments;

import java.time.LocalDate;
import java.util.UUID;

public record AppointmentResource(
        String nameSession,
        LocalDate date,
        Integer number,
        String teacher,
        Integer status,
        UUID userId,
        Integer waitingNumber
) {
    public AppointmentResource(Appointments appointments){
        this(
                appointments.getNameSession(),
                appointments.getDate(),
                appointments.getNumber(),
                appointments.getTeacher(),
                appointments.getStatus(),
                appointments.getUserId(),
                appointments.getWaitingNumber()
        );
    }

}

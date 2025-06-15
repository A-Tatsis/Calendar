package com.api.backend.service;

import com.api.backend.config.SecurityUtils;
import com.api.backend.models.Appointments;
import com.api.backend.models.repositories.AppointmentRepository;
import com.api.backend.resources.AppointmentResource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointments updateAppointment(UUID id, AppointmentResource request) {
        Appointments appointments = appointmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Appointment not found!")
        );

        if (request.nameSession() != null){
            appointments.setNameSession(request.nameSession());
        }

        if (request.date() != null) {
            appointments.setDate(request.date());
        }

        if (request.number() != null) {
            appointments.setNumber(request.number());
        }

        if (request.teacher() != null) {
            appointments.setTeacher(request.teacher());
        }

        if (request.status() != null) {
            appointments.setStatus(request.status());
        }

        if (request.userId() != null) {
            appointments.setUserId(request.userId());
        }

        if (request.waitingNumber() != null) {
            appointments.setWaitingNumber(request.waitingNumber());
        }

        return appointmentRepository.save(appointments);
    }

    public List<Appointments> findAll(){
        return appointmentRepository.findAll();
    }

    public Appointments createAppointment(AppointmentResource request) {
        Appointments appointments = new Appointments();
        appointments.setNameSession(request.nameSession());
        appointments.setDate(request.date());
        appointments.setNumber(request.number());
        appointments.setTeacher(request.teacher());
        appointments.setStatus(request.status());
        appointments.setUserId(SecurityUtils.getCurrentUserId());
        appointments.setWaitingNumber(request.waitingNumber());
        return appointmentRepository.save(appointments);
    }

    public Appointments getAppointmentById(UUID id) {
        return appointmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Appointment not found with the id " + id)
        );
    }

    public void deleteAppointmentById(UUID id) {
        if (!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Appointment not found with the id " + id);
        }
        appointmentRepository.deleteById(id);
    }
}

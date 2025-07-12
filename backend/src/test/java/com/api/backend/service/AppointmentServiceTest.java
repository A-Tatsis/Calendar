package com.api.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.api.backend.models.Appointments;
import com.api.backend.models.repositories.AppointmentRepository;
import com.api.backend.resources.AppointmentResource;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

  private final static UUID APPOINTMENT_ID = UUID.randomUUID();

  private final Appointments appointment = mock(Appointments.class);
  private final AppointmentResource appointmentResource = mock(AppointmentResource.class);

  @Mock
  AppointmentRepository appointmentRepository;

  private AppointmentService cut;

  @BeforeEach
  void setUp() {
    cut = new AppointmentService(appointmentRepository);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(appointmentRepository);
  }

  @Test
  void updateAppointment() {
    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
    when(appointmentRepository.save(appointment)).thenReturn(appointment);

    var result = cut.updateAppointment(APPOINTMENT_ID, appointmentResource);

    assertEquals(appointment, result);
    verify(appointmentRepository, times(1)).findById(APPOINTMENT_ID);
    verify(appointmentRepository, times(1)).save(appointment);
  }

  @Test
  void updateAppointmentNotFound() {
    var ex = assertThrows(EntityNotFoundException.class,
        () -> cut.updateAppointment(APPOINTMENT_ID, appointmentResource));

    assertEquals("Appointment not found!", ex.getMessage());
    verify(appointmentRepository, times(1)).findById(APPOINTMENT_ID);
  }
}
package com.api.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.api.backend.config.SecurityUtils;
import com.api.backend.models.Appointments;
import com.api.backend.models.repositories.AppointmentRepository;
import com.api.backend.resources.AppointmentResource;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

  private final static UUID APPOINTMENT_ID = UUID.randomUUID();
  private final static UUID USER_ID = UUID.randomUUID();
  private Appointments appointment;
  private final AppointmentResource appointmentResource = mock(AppointmentResource.class);

  private final SecurityUtils securityUtils = mock(SecurityUtils.class);

  @Mock
  AppointmentRepository appointmentRepository;

  private AppointmentService cut;

  @BeforeEach
  void setUp() {
    appointment = new Appointments();
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

  @Test
  void findAll() {

    List<Appointments> mockAppointment = List.of(
            new Appointments(),
            new Appointments()
    );
    when(appointmentRepository.findAll()).thenReturn(mockAppointment);

    var result = cut.findAll();

    assertEquals(result, mockAppointment);

    verify(appointmentRepository, times(1)).findAll();

  }

  @Test
  void createAppointment() {

    try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
      utilities.when(SecurityUtils::getCurrentUserId).thenReturn(USER_ID);

      var nameSession = "dummy-name-session";
      var date = LocalDate.now();
      var number = 5;
      var teacher = "dummy-teacher";
      var status = 1;
      var waitingNumber = 3;

      when(appointmentResource.nameSession()).thenReturn(nameSession);
      when(appointmentResource.date()).thenReturn(date);
      when(appointmentResource.number()).thenReturn(number);
      when(appointmentResource.teacher()).thenReturn(teacher);
      when(appointmentResource.status()).thenReturn(status);
      when(appointmentResource.waitingNumber()).thenReturn(waitingNumber);

      appointment.setNameSession(nameSession);
      appointment.setDate(date);
      appointment.setNumber(number);
      appointment.setTeacher(teacher);
      appointment.setStatus(status);
      appointment.setUserId(USER_ID);
      appointment.setWaitingNumber(waitingNumber);


      when(appointmentRepository.save(any())).thenReturn(appointment);

      var result = cut.createAppointment(appointmentResource);

      assertEquals(result, appointment);

      verify(appointmentRepository, times(1)).save(eq(appointment));
    }

  }

  @Test
  void getAppointmentById() {

    when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
    var result = cut.getAppointmentById(APPOINTMENT_ID);
    assertEquals(result, appointment);
    verify(appointmentRepository, times(1)).findById(APPOINTMENT_ID);

  }

  @Test
  void getAppointmentByIdNotFound() {
    var ex = assertThrows(EntityNotFoundException.class,
            () -> cut.getAppointmentById(APPOINTMENT_ID)
            );
    assertEquals("Appointment not found with the id " + APPOINTMENT_ID, ex.getMessage());
    verify(appointmentRepository, times(1)).findById(APPOINTMENT_ID);
  }

  @Test
  void deleteAppointmentById() {
    when(appointmentRepository.existsById(APPOINTMENT_ID)).thenReturn(true);
    cut.deleteAppointmentById(APPOINTMENT_ID);

    verify(appointmentRepository, times(1)).existsById(APPOINTMENT_ID);
    verify(appointmentRepository, times(1)).deleteById(APPOINTMENT_ID);
  }

  @Test
  void deleteAppointmentByIdNotFound() {
    var ex = assertThrows(EntityNotFoundException.class,
            () -> cut.deleteAppointmentById(APPOINTMENT_ID)
            );
    assertEquals("Appointment not found with the id " + APPOINTMENT_ID, ex.getMessage());
    verify(appointmentRepository, times(1)).existsById(APPOINTMENT_ID);
  }

}
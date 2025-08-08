package com.api.backend.service;

import com.api.backend.config.SecurityUtils;
import com.api.backend.models.Appointments;
import com.api.backend.models.ClosedAppointment;
import com.api.backend.models.Subscription;
import com.api.backend.models.UserSubscriptions;
import com.api.backend.models.repositories.AppointmentRepository;
import com.api.backend.models.repositories.ClosedAppointmentRepository;
import com.api.backend.models.repositories.SubscriptionRepository;
import com.api.backend.models.repositories.UserSubscriptionsRepository;
import com.api.backend.resources.ClosedAppointmentResource;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClosedAppointmentServiceTest {

    private final UUID APPOINTMENT_ID = UUID.randomUUID();
    private final UUID USER_ID = UUID.randomUUID();
    private static ClosedAppointment closedAppointment = mock(ClosedAppointment.class);
    private static Appointments appointments = mock(Appointments.class);
    private static ClosedAppointmentResource closedAppointmentResource = mock(ClosedAppointmentResource.class);

    @Mock
    ClosedAppointmentRepository closedAppointmentRepository;

    @Mock
    UserSubscriptionsRepository userSubscriptionsRepository;

    @Mock
    SubscriptionRepository subscriptionRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    UserSubscriptions userSubscriptions;

    @Mock
    Subscription subscription;

    private ClosedAppointmentService cut;

    @BeforeEach
    void setUp() {
        cut = new ClosedAppointmentService(closedAppointmentRepository, userSubscriptionsRepository, subscriptionRepository, appointmentRepository);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(closedAppointmentRepository, userSubscriptionsRepository, subscriptionRepository, appointmentRepository);
    }

    @Test
    void findAll() {
        List<ClosedAppointment> mockClosedAppointments = List.of(
                new ClosedAppointment(),
                new ClosedAppointment(),
                new ClosedAppointment()
        );
        when(closedAppointmentRepository.findAll()).thenReturn(mockClosedAppointments);

        var result = cut.findAll();
        assertEquals(mockClosedAppointments, result);
        verify(closedAppointmentRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(closedAppointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(closedAppointment));
        var result = cut.findById(APPOINTMENT_ID);
        assertEquals(closedAppointment, result);
        verify(closedAppointmentRepository, times(1)).findById(APPOINTMENT_ID);
    }

    @Test
    void findByUser() {
        try(MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(USER_ID);
            List<ClosedAppointment> mockClosedAppointments = List.of(
                    new ClosedAppointment(),
                    new ClosedAppointment(),
                    new ClosedAppointment(),
                    new ClosedAppointment()
            );
            when(closedAppointmentRepository.findAllByIdUser(USER_ID)).thenReturn(mockClosedAppointments);
            var result = cut.findByUser();
            assertEquals(mockClosedAppointments, result);
            verify(closedAppointmentRepository, times(1)).findAllByIdUser(USER_ID);
        }
    }

    @Test
    void countSpecificAClosedAppointment() {
        List<Object[]> mockResults = List.of(
                new Object[]{1, 5},
                new Object[]{2, 3}
        );
        when(closedAppointmentRepository.countStatusesByAppointment(APPOINTMENT_ID)).thenReturn(mockResults);

        Map<Integer, Integer> result = cut.countSpecificAClosedAppointment(APPOINTMENT_ID);
        assertEquals(2, result.size());
        assertEquals(5, result.get(1));
        assertEquals(3, result.get(2));
    }

    @Test
    void countSpecificUClosedAppointment() {
        List<Object[]> mockResults = List.of(
                new Object[]{1, 5},
                new Object[]{2, 3}
        );
        when(closedAppointmentRepository.countStatusesByUser(USER_ID)).thenReturn(mockResults);
        Map<Integer, Integer> result = cut.countSpecificUClosedAppointment(USER_ID);
        assertEquals(2, result.size());
        assertEquals(5, result.get(1));
        assertEquals(3, result.get(2));

    }

    @Test
    void createClosedAppointment() {
        try(MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(USER_ID);

            when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointments));
            when(closedAppointmentRepository.countByIdAppointmentAndStatusIn(APPOINTMENT_ID, List.of(1))).thenReturn(10L);
            when(userSubscriptionsRepository.findTopByIdUserAndStatusOrderByRegisteredDateDesc(USER_ID, 1)).thenReturn(Optional.of(userSubscriptions));

            when(subscriptionRepository.findById(userSubscriptions.getSubscription())).thenReturn(Optional.of(subscription));
            when(subscription.getNumberSessions()).thenReturn(10);
            when(closedAppointmentRepository.countByIdUserAndStatusIn(USER_ID, List.of(1, 2))).thenReturn(9L);

            when(closedAppointmentResource.idAppointment()).thenReturn(APPOINTMENT_ID);
            when(closedAppointmentResource.status()).thenReturn(1);
            when(closedAppointmentResource.idUser()).thenReturn(USER_ID);
            when(closedAppointmentResource.registeredDate()).thenReturn(LocalDateTime.now());

            closedAppointment.setIdAppointment(APPOINTMENT_ID);
            closedAppointment.setStatus(1);
            closedAppointment.setIdUser(USER_ID);
            closedAppointment.setRegisteredDate(LocalDateTime.now());

            when(closedAppointmentRepository.save(any())).thenReturn(closedAppointment);

            var result = cut.createClosedAppointment(closedAppointmentResource);

            assertEquals(closedAppointment, result);

            verify(closedAppointmentRepository, times(1)).save(eq(closedAppointment));


        }
    }


}

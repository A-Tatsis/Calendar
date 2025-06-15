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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ClosedAppointmentService {

    private final ClosedAppointmentRepository closedAppointmentRepository;

    private final UserSubscriptionsRepository userSubscriptionsRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final AppointmentRepository appointmentRepository;

    public ClosedAppointmentService(ClosedAppointmentRepository closedAppointmentRepository,
                                    UserSubscriptionsRepository userSubscriptionsRepository,
                                    SubscriptionRepository subscriptionRepository,
                                    AppointmentRepository appointmentRepository) {
        this.closedAppointmentRepository = closedAppointmentRepository;
        this.userSubscriptionsRepository = userSubscriptionsRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }


    public List<ClosedAppointment> findAll() {
        return closedAppointmentRepository.findAll();
    }

    public ClosedAppointment findById(UUID id) {
        return closedAppointmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Closed Appointment not found with the id " + id)
        );
    }

    public List<ClosedAppointment> findByUser() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return closedAppointmentRepository.findAllByIdUser(userId);

    }

    public Map<Integer, Long> countSpecificAClosedAppointment(UUID id) {
        List<Object[]> results = closedAppointmentRepository.countStatusesByAppointment(id);
        Map<Integer, Long> counts = new HashMap<>();

        for (Object[] result : results) {
            Integer status = (Integer) result[0];
            Long count = (Long) result[1];
            counts.put(status, count);
        }

        return counts;
    }

    public Map<Integer, Long> countSpecificUClosedAppointment(UUID id) {
        List<Object[]> results = closedAppointmentRepository.countStatusesByUser(id);
        Map<Integer, Long> counts = new HashMap<>();

        for (Object[] result : results) {
            Integer status = (Integer) result[0];
            Long count = (Long) result[1];
            counts.put(status, count);
        }

        return counts;
    }

    public ClosedAppointment createClosedAppointment(ClosedAppointmentResource request) {
        UUID idUser = SecurityUtils.getCurrentUserId();

        if (closedAppointmentRepository.existsByIdAppointmentAndIdUser(request.idAppointment(), idUser)){
            throw new RuntimeException("User is already registered for this appointment.");
        }

        if (closedAppointmentRepository.existsByIdAppointment(request.idAppointment())){
            Appointments appointments = appointmentRepository.findById(request.idAppointment())
                    .orElseThrow(() -> new RuntimeException("Appointment not found."));

            int maxNumberOfUsers = appointments.getNumber();
            long activeAppointmentsCount = closedAppointmentRepository.countByIdAppointmentAndStatusIn(request.idAppointment(), List.of(request.status()));
            if (activeAppointmentsCount >= maxNumberOfUsers) {
                throw new RuntimeException("The appointment has maximum number of allowed users.");
            }
            UserSubscriptions userSubscriptions = userSubscriptionsRepository.findTopByIdUserAndStatusOrderByRegisteredDateDesc(idUser, 1)
                    .orElseThrow(() -> new RuntimeException("No active subscription found for user."));

            Subscription subscription = subscriptionRepository
                    .findById(userSubscriptions.getSubscription())
                    .orElseThrow(() -> new RuntimeException("Subscription not found."));

            int maxSessions = subscription.getNumberSessions();
            long activeAppointmentsCountByUser = closedAppointmentRepository
                    .countByIdUserAndStatusIn(idUser, List.of(1, 2));

            if (activeAppointmentsCountByUser >= maxSessions) {
                throw new RuntimeException("You have reached the maximum number of allowed sessions.");
            }

            ClosedAppointment closedAppointment = new ClosedAppointment();
            closedAppointment.setIdAppointment(request.idAppointment());
            closedAppointment.setStatus(request.status());
            closedAppointment.setIdUser(idUser);
            closedAppointment.setRegisteredDate(LocalDateTime.now());
            return closedAppointmentRepository.save(closedAppointment);
        }

        ClosedAppointment closedAppointment = new ClosedAppointment();
        closedAppointment.setIdAppointment(request.idAppointment());
        closedAppointment.setStatus(request.status());
        closedAppointment.setIdUser(idUser);
        closedAppointment.setRegisteredDate(LocalDateTime.now());
        return closedAppointmentRepository.save(closedAppointment);
    }

    public void canceledAppointment(UUID idAppointment) {
        UUID idUser = SecurityUtils.getCurrentUserId();
        if (!closedAppointmentRepository.existsByIdAppointmentAndIdUser(idAppointment, idUser)){
            throw new EntityNotFoundException("Error with id appointment " + idAppointment + " and with id user " + idUser);
        }

        ClosedAppointment closedAppointment = closedAppointmentRepository.findByIdAppointmentAndIdUser(idAppointment, idUser);
        // deactivate appointment for this user
        closedAppointment.setStatus(3);
        closedAppointmentRepository.save(closedAppointment);

        // Check the waiting list and prioritize the user with the earliest appointment request.
        ClosedAppointment closedAppointmentWaitingUser = closedAppointmentRepository.findFirstByIdAppointmentAndStatusOrderByRegisteredDateAsc(idAppointment, 2);
        if (closedAppointmentWaitingUser != null) {
            closedAppointmentWaitingUser.setStatus(1);
            closedAppointmentRepository.save(closedAppointmentWaitingUser);
        }

    }
}

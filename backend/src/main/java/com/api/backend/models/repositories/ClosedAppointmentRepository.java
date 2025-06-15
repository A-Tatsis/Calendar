package com.api.backend.models.repositories;


import com.api.backend.models.ClosedAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ClosedAppointmentRepository extends JpaRepository<ClosedAppointment, UUID> {

    List<ClosedAppointment> findAllByIdUser(UUID id);

    @Query("SELECT e.status, COUNT(e) FROM ClosedAppointment e WHERE e.idAppointment = :idAppointment GROUP BY e.status")
    List<Object[]> countStatusesByAppointment(@Param("idAppointment") UUID idAppointment);

    @Query("SELECT e.status, COUNT(e) FROM ClosedAppointment e WHERE e.idUser = :idUser GROUP BY e.status")
    List<Object[]> countStatusesByUser(@Param("idUser") UUID idUser);

    long countByIdUserAndStatusIn(UUID idUser, List<Integer> statuses);

    long countByIdAppointmentAndStatusIn(UUID idAppointment, List<Integer> statuses);

    boolean existsByIdAppointmentAndIdUser(UUID idAppointment, UUID idUser);

    ClosedAppointment findByIdAppointmentAndIdUser(UUID idAppointment, UUID idUser);

    ClosedAppointment findFirstByIdAppointmentAndStatusOrderByRegisteredDateAsc(UUID idAppointment, int i);

    boolean existsByIdAppointment(UUID idAppointment);
}

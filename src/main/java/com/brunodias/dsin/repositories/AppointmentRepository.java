package com.brunodias.dsin.repositories;

import com.brunodias.dsin.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a FROM Appointment a JOIN a.services s WHERE s.id = :serviceId AND a.appointmentDateTime = :appointmentDateTime")
    List<Appointment> findByServiceIdAndAppointmentDateTime(@Param("serviceId") UUID serviceId,
            @Param("appointmentDateTime") LocalDateTime appointmentDateTime);
}

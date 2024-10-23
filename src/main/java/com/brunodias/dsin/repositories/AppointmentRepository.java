package com.brunodias.dsin.repositories;

import com.brunodias.dsin.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
        @Query("SELECT s.id, s.name, s.price, a.appointmentDateTime, u.name " +
                        "FROM Appointment a " +
                        "JOIN a.client u " +
                        "JOIN a.services s " +
                        "WHERE a.appointmentDateTime = :dateTime " +
                        "AND s.id = :serviceId")
        List<Object[]> findAppointmentDetailsByDateTime(@Param("dateTime") LocalDateTime dateTime,
                        @Param("serviceId") UUID serviceId);

        // @Query("SELECT s.id, s.name, s.price, a.appointmentDateTime, u.name " +
        // "FROM Appointment a " +
        // "JOIN a.client u " +
        // "JOIN a.services sa " +
        // "JOIN sa.service s " +
        // "WHERE a.appointmentDateTime = :dateTime AND s.id = :serviceId")
        // List<Object[]> findAppointmentDetailsByDateTime(@Param("dateTime")
        // LocalDateTime dateTime, @Param("serviceId") UUID serviceId);

        // @Query("SELECT s.name, s.price, a.appointmentDateTime, u.name " +
        // "FROM Appointment a " +
        // "JOIN a.client u " +
        // "JOIN a.services sa " +
        // "JOIN sa.service s " +
        // "WHERE a.appointmentDateTime = :dateTime")
        // List<Object[]> findAppointmentDetailsByDateTime(@Param("dateTime")
        // LocalDateTime dateTime);
}

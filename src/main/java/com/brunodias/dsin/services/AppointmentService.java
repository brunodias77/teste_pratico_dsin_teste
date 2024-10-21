package com.brunodias.dsin.services;

import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.repositories.AppointmentRepository;
import com.brunodias.dsin.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final ServiceRepository _serviceRepository;
    private final AppointmentRepository _appointmentRepository;

    public List<com.brunodias.dsin.entities.Service> findExistingServices(List<UUID> serviceIds) {
        return _serviceRepository.findAllById(serviceIds);
    }

    public boolean areServicesAvailable(List<UUID> serviceIds, LocalDateTime appointmentDateTime) {
        for (UUID serviceId : serviceIds) {
            List<Appointment> conflictingAppointments = _appointmentRepository.findByServiceIdAndAppointmentDateTime(serviceId, appointmentDateTime);
            if (!conflictingAppointments.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}

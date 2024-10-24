package com.brunodias.dsin.services;

import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.repositories.AppointmentRepository;
import com.brunodias.dsin.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final ServiceRepository _serviceRepository;
    private final AppointmentRepository _appointmentRepository;

    public List<com.brunodias.dsin.entities.Service> findExistingServices(List<UUID> serviceIds) {
        return _serviceRepository.findAllById(serviceIds);
    }
    public List<Appointment> getAllAppointmentByUserId(){
       return null;
    }
}

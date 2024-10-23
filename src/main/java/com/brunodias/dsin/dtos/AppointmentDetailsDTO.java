package com.brunodias.dsin.dtos;

import com.brunodias.dsin.entities.Service;
import com.brunodias.dsin.enums.AppoitmentStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record AppointmentDetailsDTO(
        LocalDateTime appointmentDateTime,
        AppoitmentStatus status,
        String clientName,
        String clientPhoneNumber,
        Set<Service> services
) {
}

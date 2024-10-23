package com.brunodias.dsin.useCases.appointments.getDetailsAppointment;

import com.brunodias.dsin.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin.dtos.BaseResponseDTO;
import com.brunodias.dsin.enums.AppoitmentStatus;
import com.brunodias.dsin.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.lang.Object;


@Service
@RequiredArgsConstructor
public class GetDetailsAppointment implements IGetDetailsAppointmentUseCase {
    private final AppointmentRepository _appointmentRepository;

    @Override
    public BaseResponseDTO execute(UUID appointmentId) {
        var appointmentDetails = _appointmentRepository.findAppointmentDetailsById(appointmentId);

        if (appointmentDetails.isEmpty()) {
            return BaseResponseDTO.builder()
                    .status(404)
                    .message("Appointment not found")
                    .build();
        }

        Object[] result = appointmentDetails.get(0);

        LocalDateTime appointmentDateTime = (LocalDateTime) result[1];
        AppoitmentStatus status = (AppoitmentStatus) result[2];
        String clientName = (String) result[3];
        String clientPhoneNumber = (String) result[4];

        Set<com.brunodias.dsin.entities.Service> services = new HashSet<>();
        if (result[5] instanceof com.brunodias.dsin.entities.Service service) {
            services.add(service);
        }

        var appointmentDetailsDTO = new AppointmentDetailsDTO(
                appointmentDateTime,
                status,
                clientName,
                clientPhoneNumber,
                services
        );

        return BaseResponseDTO.builder()
                .status(200)
                .data(appointmentDetailsDTO)
                .build();
    }
}


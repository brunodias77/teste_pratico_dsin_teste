package com.brunodias.dsin.useCases.appointments.updateAppointment;

import com.brunodias.dsin.communications.appointments.RequestUpdateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;
import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.repositories.AppointmentRepository;
import com.brunodias.dsin.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateApppointmentUseCase implements IUpdateAppointmentUseCase {

    private final UserService _userService;
    private final AppointmentRepository _appointmentRepository;

    @Override
    public BaseResponseDTO execute(UUID appointmentId, RequestUpdateAppointment request) {
        var user = _userService.getLoginUser();
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment == null) {
            return BaseResponseDTO.builder()
                    .status(400)
                    .message("Agendamento não encontrado!")
                    .build();
        }
        if (!isUpdateAllowed(appointment.getAppointmentDateTime())) {
            return BaseResponseDTO.builder()
                    .status(400)
                    .message("O agendamento só pode ser alterado até 2 dias antes da data marcada. Para alterações, contate pelo telefone.")
                    .build();
        }
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        _appointmentRepository.save(appointment);
        String formattedDateTime = formatAppointmentDateTime(request.getAppointmentDateTime());
        return BaseResponseDTO.builder()
                .status(200)
                .message("Agendamento atualizado com sucesso para " + formattedDateTime + "!")
                .build();
    }

    private Appointment getAppointmentById(UUID appointmentId) {
        return _appointmentRepository.findById(appointmentId).orElse(null);
    }

    private boolean isUpdateAllowed(LocalDateTime appointmentDateTime) {
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        long daysDifference = ChronoUnit.DAYS.between(currentDateTime.toLocalDate(), appointmentDateTime.toLocalDate());
        return daysDifference >= 2;
    }

    private String formatAppointmentDateTime(LocalDateTime appointmentDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return appointmentDateTime.format(formatter);
    }

}


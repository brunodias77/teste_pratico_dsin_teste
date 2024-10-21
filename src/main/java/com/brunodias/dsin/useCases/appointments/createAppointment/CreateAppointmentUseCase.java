package com.brunodias.dsin.useCases.appointments.createAppointment;

import com.brunodias.dsin.communications.appointments.RequestCreateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;
import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.enums.AppoitmentStatus;
import com.brunodias.dsin.repositories.AppointmentRepository;
import com.brunodias.dsin.repositories.ServiceRepository;
import com.brunodias.dsin.services.AppointmentService;
import com.brunodias.dsin.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAppointmentUseCase implements ICreateAppointmentUseCase {

    private final UserService _userService;
    private final AppointmentService _appointmentService;
    private final AppointmentRepository _appointmentRepository;
    private final ServiceRepository _serviceRepository;

    @Override
    public BaseResponseDTO execute(RequestCreateAppointment request) {
        var user = _userService.getLoginUser();
        List<com.brunodias.dsin.entities.Service> existingServices = _appointmentService
                .findExistingServices(request.serviceIds());
        var response = _appointmentService.areServicesAvailable(request.serviceIds(), request.appointmentDateTime());
        if (response) {
            System.out.println("DEU BOM");
            var appointment = Appointment.builder().status(AppoitmentStatus.AGENDADO)
                    .appointmentDateTime(request.appointmentDateTime()).client(user).services(new HashSet<>()).build();
            var appointmentSaved = _appointmentRepository.save(appointment);
            return BaseResponseDTO.builder().status(200).message("Agendamento realizado com sucesso")
                    .data(appointment).build();
        } else {
            System.out.println("DEU RUIM");
        }
        return null;
    }
}

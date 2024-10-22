package com.brunodias.dsin.useCases.appointments.createAppointment;

import com.brunodias.dsin.communications.appointments.RequestCreateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;
import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.enums.AppoitmentStatus;
import com.brunodias.dsin.exceptions.ServicesNotFoundException;
import com.brunodias.dsin.exceptions.ServicesUnavailableException;
import com.brunodias.dsin.repositories.AppointmentRepository;
import com.brunodias.dsin.repositories.ServiceRepository;
import com.brunodias.dsin.services.AppointmentService;
import com.brunodias.dsin.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

        // Verifica se os serviços existem
        List<com.brunodias.dsin.entities.Service> existingServices = _appointmentService
                .findExistingServices(request.serviceIds());

        // Converte os IDs dos serviços existentes para um Set de IDs
        Set<UUID> existingServiceIds = existingServices.stream()
                .map(com.brunodias.dsin.entities.Service::getId)
                .collect(Collectors.toSet());

        // Verifica se todos os serviços solicitados existem
        if (!existingServiceIds.containsAll(request.serviceIds())) {
            throw new ServicesNotFoundException("Alguns serviços solicitados não foram encontrados.");
        }

        // Verifica se os serviços estão disponíveis
        boolean servicesAvailable = _appointmentService.areServicesAvailable(request.serviceIds(),
                request.appointmentDateTime());

        if (!servicesAvailable) {
            throw new ServicesUnavailableException("Serviços não disponíveis no horário solicitado.");
        }

        // Criação do agendamento
        var appointment = Appointment.builder()
                .status(AppoitmentStatus.AGENDADO)
                .appointmentDateTime(request.appointmentDateTime())
                .client(user)
                .services(new HashSet<>())
                .build();

        var appointmentSaved = _appointmentRepository.save(appointment);

        return BaseResponseDTO.builder()
                .status(200)
                .message("Agendamento realizado com sucesso")
                .data(appointmentSaved)
                .build();
    }
}

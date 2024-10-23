package com.brunodias.dsin.useCases.appointments.createAppointment;

import com.brunodias.dsin.communications.appointments.RequestCreateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;
import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.enums.AppoitmentStatus;
import com.brunodias.dsin.exceptions.AppointmentCreationFailure;
import com.brunodias.dsin.exceptions.ServicesNotFoundException;
import com.brunodias.dsin.exceptions.ServicesUnavailableException;
import com.brunodias.dsin.repositories.AppointmentRepository;
import com.brunodias.dsin.repositories.ServiceRepository;
import com.brunodias.dsin.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateAppointmentUseCase implements ICreateAppointmentUseCase {

        private final UserService _userService;
        private final com.brunodias.dsin.services.AppointmentService _appointmentService;
        private final AppointmentRepository _appointmentRepository;
        private final ServiceRepository _serviceRepository;

        @Override
        public BaseResponseDTO execute(RequestCreateAppointment request) {
                var user = _userService.getLoginUser();

                // Obtém o serviço e lança exceção se não encontrado
                var service = getServiceById(request.serviceId())
                                .orElseThrow(() -> new ServicesNotFoundException("Serviço não encontrado"));

                // Verifica se o horário está disponível
                var appointmentAvailable = appointmentAvailable(request.appointmentDateTime(), request.serviceId());
                if (!appointmentAvailable) {
                        throw new AppointmentCreationFailure("Horário indisponível");
                }

                // Cria o Set de serviços e adiciona o serviço recuperado
                Set<com.brunodias.dsin.entities.Service> services = new HashSet<>();
                services.add(service);

                // Cria o agendamento
                var appointment = Appointment.builder()
                                .status(AppoitmentStatus.AGENDADO)
                                .appointmentDateTime(request.appointmentDateTime())
                                .client(user)
                                .services(services)
                                .build();

                // Salva o agendamento
                var appointmentSaved = _appointmentRepository.save(appointment);

                // Retorna a resposta com sucesso
                return BaseResponseDTO.builder()
                                .status(200)
                                .message("Agendamento realizado com sucesso")
                                .data(appointmentSaved)
                                .build();
        }

        private Optional<com.brunodias.dsin.entities.Service> getServiceById(UUID serviceId) {
                return _serviceRepository.findById(serviceId);
        }

        public boolean appointmentAvailable(LocalDateTime appointmentDateTime, UUID service_id) {
                List<Object[]> existingAppointments = _appointmentRepository
                                .findAppointmentDetailsByDateTime(appointmentDateTime, service_id);
                return existingAppointments.isEmpty();
        }
}

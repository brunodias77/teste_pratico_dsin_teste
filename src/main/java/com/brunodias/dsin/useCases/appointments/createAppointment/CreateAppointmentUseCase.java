package com.brunodias.dsin.useCases.appointments.createAppointment;

import com.brunodias.dsin.communications.appointments.RequestCreateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;
import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.entities.AppointmentService;
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
                var service = getServiceById(request.serviceId());
                var appointmentAvailable = appointmentAvailable(request.appointmentDateTime(), request.serviceId());
                if (service.isEmpty()) {
                        throw new ServicesNotFoundException("Serviço não encontrado");
                }
                if (!appointmentAvailable) {
                        throw new AppointmentCreationFailure("Horário indisponível");
                }

                var appointment = Appointment.builder()
                                .status(AppoitmentStatus.AGENDADO)
                                .appointmentDateTime(request.appointmentDateTime())
                                .client(user)
                                .services(new HashSet<>())
                                .build();
                AppointmentService appointmentService = AppointmentService.builder()
                                .appointment(appointment)
                                .service(service.get())
                                .build();
                Set<AppointmentService> services = new HashSet<>();
                services.add(appointmentService);
                appointment.setServices(services);

                var appointmentSaved = _appointmentRepository.save(appointment);

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

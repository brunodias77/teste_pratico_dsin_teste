package com.brunodias.dsin.useCases.appointments.getAllAppointments;

import com.brunodias.dsin.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin.repositories.AppointmentRepository;
import com.brunodias.dsin.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllAppointmentsUseCase implements IGetAllAppointmentsUseCase{
    private final AppointmentRepository _appointmentRepository;
    private final UserService _userService;
    @Override
    public List<AppointmentDetailsDTO> execute() {
        var user = _userService.getLoginUser();
        var appointments = _appointmentRepository.findAllByClientId(user.getId());
        return List.of();
    }
}

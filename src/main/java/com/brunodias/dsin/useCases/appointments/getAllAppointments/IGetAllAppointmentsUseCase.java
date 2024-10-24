package com.brunodias.dsin.useCases.appointments.getAllAppointments;

import com.brunodias.dsin.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin.dtos.BaseResponseDTO;

import java.util.List;


public interface IGetAllAppointmentsUseCase {
    public List<AppointmentDetailsDTO> execute();

}

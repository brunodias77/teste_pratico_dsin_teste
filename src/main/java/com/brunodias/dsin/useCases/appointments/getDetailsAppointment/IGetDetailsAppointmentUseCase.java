package com.brunodias.dsin.useCases.appointments.getDetailsAppointment;

import com.brunodias.dsin.communications.appointments.RequestCreateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;

import java.util.UUID;

public interface IGetDetailsAppointmentUseCase {
    public BaseResponseDTO execute(UUID appointmentId);

}

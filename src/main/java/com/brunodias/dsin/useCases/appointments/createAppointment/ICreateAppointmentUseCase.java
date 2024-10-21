package com.brunodias.dsin.useCases.appointments.createAppointment;

import com.brunodias.dsin.communications.appointments.RequestCreateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;

public interface ICreateAppointmentUseCase {
    public BaseResponseDTO execute(RequestCreateAppointment request);
}

package com.brunodias.dsin.useCases.appointments.updateAppointment;

import com.brunodias.dsin.communications.appointments.RequestUpdateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;

import java.util.UUID;

public interface IUpdateAppointmentUseCase {
    public BaseResponseDTO execute(UUID appointmentId, RequestUpdateAppointment request);

}

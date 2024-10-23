package com.brunodias.dsin.mappers;

import com.brunodias.dsin.dtos.AppointmentDetailsDTO;
import com.brunodias.dsin.dtos.UserDTO;
import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.entities.User;
import org.springframework.stereotype.Component;

@Component
public class EntityToDtoMapper {

    public UserDTO mapUserToDtoBasic(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        return userDto;

    }

    public AppointmentDetailsDTO mapAppointmentToDto(Appointment appointment){
        var appointmentDetails = new AppointmentDetailsDTO(appointment.getAppointmentDateTime(), appointment.getStatus(), appointment.getClient().getName(), appointment.getClient().getPhoneNumber(), appointment.getServices());
        return appointmentDetails;
    }
}

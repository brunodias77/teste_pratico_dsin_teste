package com.brunodias.dsin.mappers;

import com.brunodias.dsin.dtos.UserDTO;
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
}

package com.brunodias.dsin.useCases.users.register;

import com.brunodias.dsin.communications.users.RequestRegisterUser;
import com.brunodias.dsin.dtos.BaseResponseDTO;
import com.brunodias.dsin.entities.Role;
import com.brunodias.dsin.entities.User;
import com.brunodias.dsin.exceptions.UserFoundException;
import com.brunodias.dsin.mappers.EntityToDtoMapper;
import com.brunodias.dsin.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase implements IRegisterUserUseCase{

    private final UserRepository _userRepository;
    private final EntityToDtoMapper _entityToDtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BaseResponseDTO execute(RequestRegisterUser request) {
        this._userRepository.findByEmail(request.email()).ifPresent((user) -> {
            throw new UserFoundException();
        });
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .roles(new HashSet<>())
                .build();
        var userRole = Role.builder().name("ROLE_USER").build();
        user.getRoles().add(userRole);
        var savedUser = _userRepository.save(user);
        var userDto = _entityToDtoMapper.mapUserToDtoBasic(savedUser);

        return BaseResponseDTO.builder()
                .status(200)
                .message("Usuário adicionado com sucesso")
                .build();
    }
}

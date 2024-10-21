package com.brunodias.dsin.useCases.users.register;

import com.brunodias.dsin.communications.users.RequestRegisterUser;
import com.brunodias.dsin.dtos.BaseResponseDTO;

public interface IRegisterUserUseCase {

    public BaseResponseDTO execute(RequestRegisterUser request);

}

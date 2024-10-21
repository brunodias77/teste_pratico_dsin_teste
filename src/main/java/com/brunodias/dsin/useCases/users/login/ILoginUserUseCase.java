package com.brunodias.dsin.useCases.users.login;

import com.brunodias.dsin.communications.users.RequestLoginUser;
import com.brunodias.dsin.dtos.BaseResponseDTO;

public interface ILoginUserUseCase {
    public BaseResponseDTO execute(RequestLoginUser request);
}

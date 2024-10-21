package com.brunodias.dsin.communications.users;

public record RequestRegisterUser(
        String name,
        String email,
        String phoneNumber,
        String password
) {
}

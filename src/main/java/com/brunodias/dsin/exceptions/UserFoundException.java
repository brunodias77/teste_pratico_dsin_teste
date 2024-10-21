package com.brunodias.dsin.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(){
        super("Usuario ja existe !");
    }
}
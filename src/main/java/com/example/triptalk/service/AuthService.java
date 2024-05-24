package com.example.triptalk.service;

import com.example.triptalk.dto.SignupRequestDto;
import com.example.triptalk.dto.UserInfoDto;
import com.example.triptalk.exception.InputValueException;

public interface AuthService {
    int MIN_USERNAME_LENGTH=8;
    int MAX_USERNAME_LENGTH=15;
    int MIN_PASSWORD_LENGTH=12;
    int MAX_PASSWORD_LENGTH=20;
    int MAX_EMAIL_LENGTH=100;

    UserInfoDto registerUser(SignupRequestDto signUpRequestDTO) throws InputValueException;
}

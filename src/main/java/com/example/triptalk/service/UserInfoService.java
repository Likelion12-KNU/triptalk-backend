package com.example.triptalk.service;

import com.example.triptalk.dto.UserInfoDto;

public interface UserInfoService {
    int MIN_NICKNAME_LENGTH=2;
    int MAX_NICKNAME_LENGTH=10;

    UserInfoDto read(String id);
}

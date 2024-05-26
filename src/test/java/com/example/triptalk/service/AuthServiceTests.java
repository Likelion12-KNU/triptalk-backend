package com.example.triptalk.service;

import com.example.triptalk.dto.SignupRequestDto;
import com.example.triptalk.dto.UserInfoDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Log4j2
public class AuthServiceTests {
    @Autowired
    private AuthService authService;

    @Test
    public void testRegisterUser(){
        SignupRequestDto signUpRequestDTO= SignupRequestDto.builder()
                .username("testuser1")
                .password("testpassword1")
                .nickname("밤샘코딩")
                .build();
        UserInfoDto result=authService.registerUser(signUpRequestDTO);
        log.info(result);
    }
}
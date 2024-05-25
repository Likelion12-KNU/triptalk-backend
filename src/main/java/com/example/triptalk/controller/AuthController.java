package com.example.triptalk.controller;

import com.example.triptalk.dto.SignupRequestDto;
import com.example.triptalk.dto.UserInfoDto;
import com.example.triptalk.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    /**
     * 회원가입
     * @param signUpRequestDTO 회원가입 정보
     * @return 등록된 유저 정보
     */
    @ApiOperation("회원가입")
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDto> signUp(@Valid @RequestBody SignupRequestDto signUpRequestDTO){
        UserInfoDto userInfoDTO=authService.registerUser(signUpRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfoDTO);
    }
}

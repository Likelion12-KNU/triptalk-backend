package com.example.triptalk.controller;

import com.example.triptalk.dto.SignupRequestDto;
import com.example.triptalk.dto.UserInfoDto;
import com.example.triptalk.exception.ErrorResponse;
import com.example.triptalk.security.UserDetailsImpl;
import com.example.triptalk.service.AuthService;
import com.example.triptalk.service.UserInfoService;
import com.example.triptalk.util.JwtUtil;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserInfoService userInfoService;

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

    /**
     * 현재 로그인 상태인지 확인
     * @return 사용자 정보 (로그아웃 상태면 null)
     */
    @ApiOperation("인증 정보 확인")
    @GetMapping("/check")
    public ResponseEntity<UserInfoDto> checkAuth(){
        // userId 추출
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = null;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        }

        // userInfo 조회
        if(userId!=null){
            UserInfoDto userInfoDto=userInfoService.read(userId);
            return ResponseEntity.status(HttpStatus.OK).body(userInfoDto);
        }
        else return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

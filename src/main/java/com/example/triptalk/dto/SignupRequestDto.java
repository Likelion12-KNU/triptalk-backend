package com.example.triptalk.dto;

import com.example.triptalk.service.AuthService;
import com.example.triptalk.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    @NotBlank
    @Size(min= AuthService.MIN_USERNAME_LENGTH, max=AuthService.MAX_USERNAME_LENGTH)
    private String username;
    @NotBlank
    @Size(min=AuthService.MIN_PASSWORD_LENGTH, max=AuthService.MAX_PASSWORD_LENGTH)
    private String password;
    @Email
    @Size(max=AuthService.MAX_EMAIL_LENGTH)
    private String email;
    @NotBlank
    @Size(min= UserInfoService.MIN_NICKNAME_LENGTH, max=UserInfoService.MAX_NICKNAME_LENGTH)
    private String nickname;
}

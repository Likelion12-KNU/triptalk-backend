package com.example.triptalk.dto;

import com.example.triptalk.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String id;
    @Size(min = UserInfoService.MIN_NICKNAME_LENGTH, max = UserInfoService.MAX_NICKNAME_LENGTH)
    private String nickname;

    public UserInfoDto(UserInfoDto copy){
        this.id=copy.id;
        this.nickname=copy.nickname;
    }
}

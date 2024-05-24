package com.example.triptalk.service;

import com.example.triptalk.dto.UserInfoDto;
import com.example.triptalk.entity.UserInfo;
import com.example.triptalk.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final ModelMapper modelMapper;

    /**
     * 사용자 조회
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    @Override
    public UserInfoDto read(String userId){
        UserInfo userInfo=userInfoRepository.findById(userId).orElseThrow();
        return modelMapper.map(userInfo, UserInfoDto.class);
    }
}

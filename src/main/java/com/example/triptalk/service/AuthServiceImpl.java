package com.example.triptalk.service;

import com.example.triptalk.dto.SignupRequestDto;
import com.example.triptalk.dto.UserInfoDto;
import com.example.triptalk.entity.User;
import com.example.triptalk.entity.UserInfo;
import com.example.triptalk.exception.InputValueException;
import com.example.triptalk.repository.UserInfoRepository;
import com.example.triptalk.repository.UserRepository;
import com.example.triptalk.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;
    private final Util util;

    /**
     * 회원가입
     * @param signUpRequestDTO 회원가입 정보
     * @return 유저 정보 반환
     */
    @Override
    public UserInfoDto registerUser(SignupRequestDto signUpRequestDTO) throws InputValueException {
        //아이디, 이메일, 닉네임 중복 검사
        if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
            throw new InputValueException(InputValueException.ERROR.DUPLICATE_USERNAME);
        }
        if (signUpRequestDTO.getEmail()!=null && !signUpRequestDTO.getEmail().isEmpty()) {
            if(userRepository.existsByEmail(signUpRequestDTO.getEmail()))
                throw new InputValueException(InputValueException.ERROR.DUPLICATE_EMAIL);
        }
        if(userInfoRepository.existsByNickname(signUpRequestDTO.getNickname())){
            throw new InputValueException(InputValueException.ERROR.DUPLICATE_NICKNAME);
        }

        //유저 계정 생성
        String userId= util.generateUid();
        //중복되지 않는 uid 인지 확인하는 코드가 들어가면 좋겠다.
        //어지간해서는 안 겹치겠지만 겹쳤을 때 위험하니까.

        //패스워드의 복잡성을 검사하는 코드도 필요하고
        //이메일 형식 검사와
        //실제로 존재하는 본인 이메일인지 확인하는 기능도 필요

        UserInfo userInfo= UserInfo.builder()
                .id(userId)
                .nickname(signUpRequestDTO.getNickname())
                .build();

        User user=User.builder()
                .username(signUpRequestDTO.getUsername())
                .password(encoder.encode(signUpRequestDTO.getPassword()))
                .email(signUpRequestDTO.getEmail())
                .userInfo(userInfo)
                .build();

        //User, UserInfo 는 OneToOne 으로 연결되어 있기 때문에 하나만 저장
        User result = userRepository.save(user);
        return modelMapper.map(result.getUserInfo(), UserInfoDto.class);
    }
}

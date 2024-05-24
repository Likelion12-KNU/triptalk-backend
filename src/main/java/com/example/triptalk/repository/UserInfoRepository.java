package com.example.triptalk.repository;

import com.example.triptalk.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    Boolean existsByNickname(String nickname);
}

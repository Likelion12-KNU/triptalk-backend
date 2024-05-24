package com.example.triptalk.repository;

import com.example.triptalk.entity.User;
import com.example.triptalk.entity.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testInsert(){
        String userId= "mILa4I9Yzp2nkI5g";

        UserInfo userInfo= UserInfo.builder()
                .id(userId)
                .nickname("밤샘코딩")
                .build();
        User user=User.builder()
                .username("testuser1")
                .password(encoder.encode("testpassword1"))
                .userInfo(userInfo)
                .build();

        userRepository.save(user);
    }
    @Test
    public void testDelete(){
        String username="testuser1";
        userRepository.deleteById(username);
    }
}
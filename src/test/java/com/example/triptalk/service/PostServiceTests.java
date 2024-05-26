package com.example.triptalk.service;

import com.example.triptalk.dto.PostInputDto;
import com.example.triptalk.dto.PostOutputDto;
import com.example.triptalk.entity.Post;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class PostServiceTests {
    @Autowired
    private PostService postService;

    @Test
    public void testRegister(){
        String userId="Ho7tiwRzhwbvacEz";
        PostInputDto postInputDto= PostInputDto.builder()
                .title("1번 게시글")
                .content("서비스 등록 테스트")
                .build();
        PostOutputDto result=postService.register(postInputDto, userId);
        log.info(result);
    }

    @Test
    public void testRead(){
        Long postId=1L;
        PostOutputDto result=postService.read(postId);
        log.info(result);
    }
    @Test
    public void testModify(){
        Long postId=1L;
        PostInputDto postInputDto=PostInputDto.builder()
                .title("1번 게시글 (수정)")
                .content("서비스 등록 테스트 (수정)")
                .build();
        PostOutputDto result=postService.modify(postId, postInputDto);
        log.info(result);
    }
    @Test
    public void testRemove(){
        Long postId=1L;
        postService.remove(postId);
    }
}

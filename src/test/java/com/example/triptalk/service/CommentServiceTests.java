package com.example.triptalk.service;

import com.example.triptalk.dto.CommentInputDto;
import com.example.triptalk.dto.CommentOutputDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class CommentServiceTests {
    @Autowired
    private CommentService commentService;

    @Test
    public void testRegister(){
        Long postId=1L;
        String userId="";
        CommentInputDto commentInputDto= CommentInputDto.builder()
                .content("서비스 등록 테스트")
                .build();
        CommentOutputDto result=commentService.register(postId, commentInputDto, userId);
        log.info(result);
    }

    @Test
    public void testReadAll(){
        Long postId=1L;
        List<CommentOutputDto> result=commentService.readAllByPost(postId);
        result.forEach(commentOutputDto -> log.info(commentOutputDto));
    }

    @Test
    public void testModify(){
        Long commentId=1L;
        CommentInputDto commentInputDto= CommentInputDto.builder()
                .content("서비스 수정 테스트")
                .build();
        CommentOutputDto result=commentService.modify(commentId, commentInputDto);
        log.info(result);
    }

    @Test
    public void testRemove(){
        Long commentId=1L;
        commentService.remove(commentId);
    }
}

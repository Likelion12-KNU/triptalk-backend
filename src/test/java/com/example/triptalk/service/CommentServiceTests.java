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
       try{
           Long postId=2L;
           String userId="Ho7tiwRzhwbvacEz";
           CommentInputDto commentInputDto= CommentInputDto.builder()
                   .content("댓글 등록 테스트 2")
                   .build();
           CommentOutputDto result=commentService.register(postId, commentInputDto, userId);
           log.info(result);
       }catch(Exception e){}
    }

    @Test
    public void testReadAll(){
        Long postId=2L;
        List<CommentOutputDto> result=commentService.readAllByPost(postId);
        result.forEach(commentOutputDto -> log.info(commentOutputDto));
    }

    @Test
    public void testModify(){
        try{
            Long commentId=1L;
            CommentInputDto commentInputDto= CommentInputDto.builder()
                    .content("댓글 수정 테스트")
                    .build();
            CommentOutputDto result=commentService.modify(commentId, commentInputDto);
            log.info(result);
        }catch(Exception e){}
    }

    @Test
    public void testRemove(){
        try{
            Long commentId=1L;
            commentService.remove(commentId);
        }catch(Exception e){}
    }
}

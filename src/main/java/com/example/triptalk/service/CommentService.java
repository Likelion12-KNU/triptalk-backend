package com.example.triptalk.service;

import com.example.triptalk.dto.CommentInputDto;
import com.example.triptalk.dto.CommentOutputDto;

import java.util.List;

public interface CommentService {
    int MAX_CONTENT_LENGTH=300;

    // 댓글 등록
    CommentOutputDto register(Long postId, CommentInputDto commentInputDto, String userId);
    // 특정 게시글의 모든 댓글 조회
    List<CommentOutputDto> readAllByPost(Long postId);
    // 댓글 수정
    CommentOutputDto modify(Long commentId, CommentInputDto commentInputDto);
    // 댓글 삭제
    void remove(Long commentId);

    boolean isAuthor(Long commentId, String userId);
}

package com.example.triptalk.service;

import com.example.triptalk.dto.PostInputDto;
import com.example.triptalk.dto.PostOutputDto;

import java.util.List;

public interface PostService {
    int MAX_TITLE_LENGTH=50;
    int MAX_CONTENT_LENGTH=500;

    // 게시글 등록
    PostOutputDto register(PostInputDto postInputDto);
    // 게시글 조회
    PostOutputDto read(Long id);
    // 게시글 수정
    PostOutputDto modify(Long id, PostInputDto postInputDto);
    // 게시글 삭제
    void remove(Long id);

    // 모든 게시글 조회 (추후 페이징으로 변경)
    List<PostOutputDto> readAll();
}

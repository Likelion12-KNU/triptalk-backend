package com.example.triptalk.service;

import com.example.triptalk.dto.PostInputDto;
import com.example.triptalk.dto.PostOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    /**
     * 게시글 등록 서비스
     * @param postInputDto 클라이언트에게 받은 게시글 입력
     * @return 등록된 게시글
     */
    @Override
    public PostOutputDto register(PostInputDto postInputDto){
        return null;  // 이후에 PostOutputDto를 반환하세요.
    }

    /**
     * 게시글 조회 서비스
     * @param id 조회할 게시글 ID
     * @return 해당 ID의 게시글
     */
    @Override
    public PostOutputDto read(Long id){
        return null;  // 이후에 PostOutputDto를 반환하세요.
    }

    /**
     * 게시글 수정 서비스
     * @param id 수정할 게시글 ID
     * @param postInputDto 클라이언트에게 받은 게시글 입력
     * @return 수정된 게시글
     */
    @Override
    public PostOutputDto modify(Long id, PostInputDto postInputDto){
        return null;  // 이후에 PostOutputDto를 반환하세요.
    }

    /**
     * 게시글 삭제 서비스
     * @param id 삭제할 게시글 ID
     */
    @Override
    public void remove(Long id){

    }

    /**
     * 모든 게시글 조회 (추후 페이징으로 변경)
     * @return 모든 게시글
     */
    @Override
    public List<PostOutputDto> readAll(){
        return null; // 이후에 List<PostOutputDto>를 반환하세요.
    }
}

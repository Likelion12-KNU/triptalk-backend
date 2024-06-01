package com.example.triptalk.controller;

import com.example.triptalk.dto.PageRequestDto;
import com.example.triptalk.dto.PostInputDto;
import com.example.triptalk.dto.PostOutputDto;
import com.example.triptalk.exception.TokenException;
import com.example.triptalk.security.UserDetailsImpl;
import com.example.triptalk.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @ApiOperation("게시글 등록")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<PostOutputDto> createPost(@RequestBody PostInputDto postInputDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = null;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        }
        if(userId==null){
            throw new TokenException(TokenException.TOKEN_ERROR.UNACCEPT);
        }
        PostOutputDto createdPost = postService.register(postInputDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @ApiOperation("페이징된 게시글 조회")
    @GetMapping("")
    public ResponseEntity<List<PostOutputDto>> getPages(
            @RequestParam(defaultValue = "0") int num,
            @RequestParam(defaultValue = "10") int size) {

        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .num(num)
                .size(size)
                .build();

        List<PostOutputDto> pageResponseDto = postService.getPages(pageRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDto);
    }


    @ApiOperation("특정 게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostOutputDto> getPost(@PathVariable Long postId) {
        PostOutputDto getPost = postService.read(postId);
        return ResponseEntity.status(HttpStatus.OK).body(getPost);
    }

    @ApiOperation("게시글 수정")
    @PreAuthorize("@postServiceImpl.isAuthor(#postId, principal)")
    @PutMapping("/{postId}")
    public ResponseEntity<PostOutputDto> updatePost(@PathVariable Long postId, @RequestBody PostInputDto postInputDto) {
        PostOutputDto updatedPost = postService.modify(postId, postInputDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @ApiOperation("게시글 삭제")
    @PreAuthorize("@postServiceImpl.isAuthor(#postId, principal)")
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostOutputDto> deletePost(@PathVariable Long postId) {
        postService.remove(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

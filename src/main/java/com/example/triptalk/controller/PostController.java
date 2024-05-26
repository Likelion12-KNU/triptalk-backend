package com.example.triptalk.controller;

import com.example.triptalk.dto.PostInputDto;
import com.example.triptalk.dto.PostOutputDto;
import com.example.triptalk.exception.TokenException;
import com.example.triptalk.security.UserDetailsImpl;
import com.example.triptalk.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation("새 게시글 작성")
    @PreAuthorize("isAuthenticated()")
    @PostMapping()
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

    @ApiOperation("모든 게시글 조회")
    @GetMapping()
    public ResponseEntity<List<PostOutputDto>> getAllPosts() {
        List<PostOutputDto> getAllPosts = postService.readAll();
        return ResponseEntity.status(HttpStatus.OK).body(getAllPosts);
    }

    @ApiOperation("특정 게시글 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PostOutputDto> getPost(@PathVariable long id) {
        PostOutputDto getPost = postService.read(id);
        return ResponseEntity.status(HttpStatus.OK).body(getPost);
    }

    @ApiOperation("특정 게시글 수정")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<PostOutputDto> updatePost(@PathVariable long id, @RequestBody PostInputDto postInputDto) {
        PostOutputDto updatedPost = postService.modify(id, postInputDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @ApiOperation("특정 게시글 삭제")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<PostOutputDto> deletePost(@PathVariable long id) {
        postService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

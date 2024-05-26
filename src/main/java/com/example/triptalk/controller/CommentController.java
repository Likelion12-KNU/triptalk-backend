package com.example.triptalk.controller;

import com.example.triptalk.dto.CommentInputDto;
import com.example.triptalk.dto.CommentOutputDto;
import com.example.triptalk.exception.TokenException;
import com.example.triptalk.security.UserDetailsImpl;
import com.example.triptalk.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    @ApiOperation("특정 게시글 댓글 목록 조회")
    @GetMapping("")
    public ResponseEntity<List<CommentOutputDto>> list(@PathVariable Long postId){
        List<CommentOutputDto> commentOutputDtos=commentService.readAllByPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(commentOutputDtos);
    }

    @ApiOperation("특정 게시글 댓글 등록")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value="", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentOutputDto> create(@PathVariable Long postId, @Valid @RequestBody CommentInputDto commentInputDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId=null;
        if(principal instanceof UserDetailsImpl){
            userId=((UserDetailsImpl)principal).getId();
        }
        if(userId==null){
            throw new TokenException(TokenException.TOKEN_ERROR.UNACCEPT);
        }
        CommentOutputDto commentOutputDto=commentService.register(postId, commentInputDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentOutputDto);
    }

    @ApiOperation("특정 댓글 수정")
    @PreAuthorize("@commentServiceImpl.isAuthor(#commentId, principal)")
    @PutMapping(value="/{commentId}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentOutputDto> update(@PathVariable Long postId, @PathVariable Long commentId, @Valid @RequestBody CommentInputDto commentInputDto){
        CommentOutputDto commentOutputDto=commentService.modify(commentId, commentInputDto);
        return ResponseEntity.status(HttpStatus.OK).body(commentOutputDto);
    }

    @ApiOperation("특정 댓글 삭제")
    @PreAuthorize("@commentServiceImpl.isAuthor(#commentId, principal)")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId){
        commentService.remove(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

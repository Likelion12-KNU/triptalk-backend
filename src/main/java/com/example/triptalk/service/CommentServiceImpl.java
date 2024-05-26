package com.example.triptalk.service;

import com.example.triptalk.dto.CommentInputDto;
import com.example.triptalk.dto.CommentOutputDto;
import com.example.triptalk.dto.PostOutputDto;
import com.example.triptalk.entity.Comment;
import com.example.triptalk.entity.Post;
import com.example.triptalk.entity.UserInfo;
import com.example.triptalk.exception.TokenException;
import com.example.triptalk.repository.CommentRepository;
import com.example.triptalk.repository.PostRepository;
import com.example.triptalk.repository.UserInfoRepository;
import com.example.triptalk.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserInfoRepository userInfoRepository;
    private final ModelMapper modelMapper;

    /**
     * 게시글 등록
     * @param postId 게시글 ID
     * @param commentInputDto 클라이언트에게 받은 댓글 입력
     * @param userId 유저 ID
     * @return 등록된 게시글
     */
    @Override
    public CommentOutputDto register(Long postId, CommentInputDto commentInputDto, String userId){
        UserInfo userInfo=userInfoRepository.findById(userId).orElseThrow();
        Post post=postRepository.findById(postId).orElseThrow();

        Comment comment=Comment.builder()
                .content(commentInputDto.getContent())
                .author(userInfo)
                .post(post)
                .build();

        commentRepository.save(comment);

        return CommentOutputDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(post.getId())
                .nickname(userInfo.getNickname())
                .build();
    }

    /**
     * 특정 게시글의 모든 댓글 조회
     * @param postId 게시글 ID
     * @return 특정 게시글의 모든 댓글 목록
     */
    @Override
    public List<CommentOutputDto> readAllByPost(Long postId){
        List<Comment> comments=commentRepository.findAllByPostId(postId);
        List<CommentOutputDto> commentOutputDtos= comments.stream()
                .map(comment->{
                    CommentOutputDto commentOutputDto=modelMapper.map(comment, CommentOutputDto.class);
                    commentOutputDto.setNickname(comment.getAuthor().getNickname());
                    return commentOutputDto;
                })
                .collect(Collectors.toList());
        return commentOutputDtos;
    }

    /**
     * 댓글 수정
     * @param commentId 수정할 댓글 ID
     * @param commentInputDto 클라이언트에게 받은 댓글 입력
     * @return 수정된 댓글
     */
    @Override
    public CommentOutputDto modify(Long commentId, CommentInputDto commentInputDto){
        Comment comment=commentRepository.findById(commentId).orElseThrow();
        comment.changeContent(commentInputDto.getContent());
        comment=commentRepository.save(comment);

        return CommentOutputDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .nickname(comment.getAuthor().getNickname())
                .build();
    }

    /**
     * 댓글 삭제
     * @param commentId 댓글 ID
     */
    @Override
    public void remove(Long commentId){
        commentRepository.deleteById(commentId);
    }

    /**
     * 사용자가 댓글의 작성자가 맞는지 검사
     * @param commentId 댓글 ID
     * @param userId 사용자 ID
     * @return 사용자가 댓글의 작성자가 맞는지 여부
     */
    @Override
    public boolean isAuthor(Long commentId, Object principal) throws TokenException {
        String userId = null;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        }
        if(userId==null){
            throw new TokenException(TokenException.TOKEN_ERROR.UNACCEPT);
        }
        Comment comment=commentRepository.findById(commentId).orElseThrow();
        return comment.getAuthor().getId().equals(userId);
    }
}

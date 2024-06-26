package com.example.triptalk.service;

import com.example.triptalk.dto.PostInputDto;
import com.example.triptalk.dto.PostOutputDto;
import com.example.triptalk.entity.Comment;
import com.example.triptalk.entity.Post;
import com.example.triptalk.entity.UserInfo;
import com.example.triptalk.exception.TokenException;
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
public class PostServiceImpl implements PostService {
    private final UserInfoRepository userInfoRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    /**
     * 게시글 등록 서비스
     * @param postInputDto 클라이언트에게 받은 게시글 입력
     * @return PostOutputDto 등록된 게시글
     */
    @Override
    public PostOutputDto register(PostInputDto postInputDto, String userId){
        UserInfo userInfo=userInfoRepository.findById(userId).orElseThrow();

        Post post = Post.builder()
                .title(postInputDto.getTitle())
                .content(postInputDto.getContent())
                .author(userInfo)
                .build();

        postRepository.save(post);

        return PostOutputDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(userInfo.getNickname())
                .build();
    }

    /**
     * 게시글 조회 서비스
     * @param id 조회할 게시글 ID
     * @return PostOutputDto 해당 ID의 게시글
     */
    @Override
    public PostOutputDto read(Long id){
        Post post = postRepository.findById(id).orElseThrow();
        return PostOutputDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getAuthor().getNickname())
                .build();
    }

    /**
     * 게시글 수정 서비스
     * @param id 수정할 게시글 ID
     * @param postInputDto 클라이언트에게 받은 게시글 입력
     * @return PostOutputDto 수정된 게시글
     */
    @Override
    public PostOutputDto modify(Long id, PostInputDto postInputDto){
        Post post = postRepository.findById(id).orElseThrow();
        post.changeTitle(postInputDto.getTitle());
        post.changeContent(postInputDto.getContent());
        post = postRepository.save(post);

        return PostOutputDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getAuthor().getNickname())
                .build();
    }

    /**
     * 게시글 삭제 서비스
     * @param id 삭제할 게시글 ID
     */
    @Override
    public void remove(Long id){
        postRepository.deleteById(id);
    }

    /**
     * 모든 게시글 조회 (추후 페이징으로 변경)
     * @return List<PostOutputDto> 모든 게시글
     */
    @Override
    public List<PostOutputDto> readAll(){
        List<Post> posts = postRepository.findAll();
        List<PostOutputDto> postOutputDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostOutputDto.class))
                .collect(Collectors.toList());
        return postOutputDtos;
    }

    /**
     * 사용자가 게시글의 작성자가 맞는지 검사
     * @param postId 게시글 ID
     * @param principal 인증 정보
     * @return 사용자가 게시글의 작성자가 맞는지 여부
     */
    @Override
    public boolean isAuthor(Long postId, Object principal) throws TokenException{
        String userId = null;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        }
        if(userId==null){
            throw new TokenException(TokenException.TOKEN_ERROR.UNACCEPT);
        }
        Post post=postRepository.findById(postId).orElseThrow();
        return post.getAuthor().getId().equals(userId);
    }
}

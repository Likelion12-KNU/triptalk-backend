package com.example.triptalk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentOutputDto {
    private Long id;
    private Long postId;
    private String content;
    private String nickname;
}

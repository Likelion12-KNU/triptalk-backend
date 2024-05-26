package com.example.triptalk.dto;

import com.example.triptalk.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostInputDto {
    @NotNull
    @Size(max=PostService.MAX_TITLE_LENGTH)
    private String title;
    @NotNull
    @Size(max=PostService.MAX_CONTENT_LENGTH)
    private String content;
}

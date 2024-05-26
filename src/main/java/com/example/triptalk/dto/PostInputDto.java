package com.example.triptalk.dto;

import lombok.Getter;

@Getter
public class PostInputDto {
    private String title;
    private String content;
    private String nickname;

    public PostInputDto(String title, String content, String nickname) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }
}

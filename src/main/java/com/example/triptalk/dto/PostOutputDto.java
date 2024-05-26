package com.example.triptalk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostOutputDto {
    public long id;
    public String title;
    public String content;
    public String nickname;

    public PostOutputDto(long id, String title, String content, String nickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }
}

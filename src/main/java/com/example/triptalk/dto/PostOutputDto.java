package com.example.triptalk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOutputDto {
    public long id;
    public String title;
    public String content;
    public String nickname;
}

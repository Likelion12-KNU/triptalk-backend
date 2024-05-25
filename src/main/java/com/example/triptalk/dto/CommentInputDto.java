package com.example.triptalk.dto;

import com.example.triptalk.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentInputDto {
    @NotBlank
    @Size(max= CommentService.MAX_CONTENT_LENGTH)
    private String content;
}

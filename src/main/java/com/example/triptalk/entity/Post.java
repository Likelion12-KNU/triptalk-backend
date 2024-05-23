package com.example.triptalk.entity;

import com.example.triptalk.service.PostService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude= "author")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length= PostService.MAX_TITLE_LENGTH, nullable=false)
    private String title;
    @Column(length= PostService.MAX_CONTENT_LENGTH, nullable=false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private UserInfo author;
}

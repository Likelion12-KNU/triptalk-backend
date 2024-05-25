package com.example.triptalk.entity;

import javax.persistence.*;

import com.example.triptalk.service.CommentService;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="comment",indexes={
        @Index(name="idx_comment_post_pno", columnList="post_id"),
        @Index(name="idx_comment_writer_uid", columnList = "author_id")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude= {"post", "author"})
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length= CommentService.MAX_CONTENT_LENGTH, nullable=false)
    private String content;
    @ManyToOne(fetch= FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private UserInfo author;
    @ManyToOne(fetch= FetchType.LAZY)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Post post;

    public void changeContent(String content){
        this.content=content;
    }
}
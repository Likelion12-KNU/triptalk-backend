package com.example.triptalk.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nickname")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends BaseEntity {
    @Id
    @Column(length= 16, nullable = false)
    private String id;
    @Column(length= 10, nullable = false)
    private String nickname;
}
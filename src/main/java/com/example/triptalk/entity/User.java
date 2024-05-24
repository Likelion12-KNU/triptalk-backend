package com.example.triptalk.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_auth", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @Column(length= 15, nullable=false)
    private String username;
    @Column(nullable=false)
    private String password;
    @Column(length=100)
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private UserInfo userInfo;
}
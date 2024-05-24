package com.example.triptalk.security;


import com.example.triptalk.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;

    public static UserDetailsImpl build(User user){
        return new UserDetailsImpl(
                user.getUserInfo().getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return new HashSet<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

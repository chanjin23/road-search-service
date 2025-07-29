package com.didim.project1.user.entity;


import com.didim.project1.user.dto.UserSignUpRequestDto;
import com.didim.project1.user.dto.UserSignUpResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private boolean isDeleted;

    public UserSignUpResponseDto toSignUpResponseDto() {
        return UserSignUpResponseDto.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .build();
    }
}

package com.didim.project1.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpResponseDto {

    private Long id;
    private String email;
    private String name;
}

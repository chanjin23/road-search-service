package com.didim.project1.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}

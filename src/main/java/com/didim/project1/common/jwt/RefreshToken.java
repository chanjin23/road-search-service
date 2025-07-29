package com.didim.project1.common.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {

    private String refreshToken;
    private String email;
    private Long issuedAt;
    private Long expiredAt;
}

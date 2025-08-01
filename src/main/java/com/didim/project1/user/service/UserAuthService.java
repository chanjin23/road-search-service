package com.didim.project1.user.service;

import com.didim.project1.common.jwt.JwtToken;
import com.didim.project1.common.jwt.JwtTokenProvider;
import com.didim.project1.user.dto.UserSignInRequestDto;
import com.didim.project1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인
    @Transactional
    public JwtToken login(UserSignInRequestDto signInRequestDto) {
        User user = validateUser(signInRequestDto);
        Authentication authentication = authenticateUser(user.getEmail(), signInRequestDto.getPassword());
        return jwtTokenProvider.generateToken(authentication);
    }

    // email, password를 사용해서 유저 확인
    private User validateUser(UserSignInRequestDto signInRequestDto) {
        User user = userService.findByEmail(signInRequestDto.getEmail());

        if (!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    // 이메일, authentication 생성
    private Authentication authenticateUser(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}

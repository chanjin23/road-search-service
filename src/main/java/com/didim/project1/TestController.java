package com.didim.project1;

import com.didim.project1.user.entity.User;
import com.didim.project1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final UserRepository userRepository;

    @GetMapping("/all")
    public User findListPermitAll() {
        User user = userRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new ResponseEntity<>(user, HttpStatus.OK).getBody();
    }

    @GetMapping("/authenticated")
    public User findListAuthenticatedUser() {
        User user = userRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new ResponseEntity<>(user, HttpStatus.OK).getBody();
    }
}

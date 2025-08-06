package com.didim.project1.user.repository;

import com.didim.project1.user.dto.UserUpdateRequestDto;
import com.didim.project1.user.mapper.UserMapper;
import com.didim.project1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;

    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public void save(User user) {
        userMapper.save(user);
    }

    public Optional<User> findById(Long userId) {
        return userMapper.findById(userId);
    }

    public void update(long userId, String newPassword) {
        userMapper.update(userId, newPassword);
    }
}

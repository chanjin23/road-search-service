package com.didim.project1.user.mapper;

import com.didim.project1.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findByEmail(String email);

    void save(User user);

    Optional<User> findById(Long userId);
}

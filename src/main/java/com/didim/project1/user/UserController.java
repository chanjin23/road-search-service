package com.didim.project1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;

    @GetMapping()
    public List<User> list() {
        List<User> list = userMapper.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK).getBody();
    }

    @PostMapping()
    public ResponseEntity<?> insert(@RequestParam Long id,
                                    @RequestParam String username,
                                    @RequestParam String password) {
        User users = new User(id, username, password);
//        User users = new User(1L, "홍길동", "123");
        userMapper.insert(users);
        return ResponseEntity.status(HttpStatus.OK).body("insert OK");
    }
}

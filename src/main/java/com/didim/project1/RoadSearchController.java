package com.didim.project1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RoadSearchController {

    @GetMapping("/")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("good");
    }
}

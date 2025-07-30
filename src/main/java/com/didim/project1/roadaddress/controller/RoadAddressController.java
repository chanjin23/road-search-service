package com.didim.project1.roadaddress.controller;

import com.didim.project1.roadaddress.dto.RoadAddressResponseDto;
import com.didim.project1.roadaddress.service.RoadAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class RoadAddressController {

    private final RoadAddressService roadAddressService;

    @GetMapping("/search")
    public ResponseEntity<?> searchRoadAddress(
            @RequestParam("roadAddress") String roadAddress
    ) throws URISyntaxException {
        List<RoadAddressResponseDto> roadAddressResponseDto
                = roadAddressService.fetchRoadAddress(roadAddress);

        log.info("조회된 결과 수 : {}", roadAddressResponseDto.size());
        return ResponseEntity.status(HttpStatus.OK).body(roadAddressResponseDto);
    }
}

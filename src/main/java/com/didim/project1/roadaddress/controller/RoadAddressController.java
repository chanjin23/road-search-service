package com.didim.project1.roadaddress.controller;

import com.didim.project1.roadaddress.dto.RoadAddressApiDto;
import com.didim.project1.roadaddress.dto.RoadAddressDto;
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
    public ResponseEntity<List<RoadAddressDto>> searchRoadAddress(
            @RequestParam("roadAddress") String roadAddress
//            @RequestParam(value = "page", defaultValue = "1") int page
    ) throws URISyntaxException {
        RoadAddressApiDto roadAddressApiDto = roadAddressService.fetchRoadAddress(roadAddress);
        List<RoadAddressDto> roadAddressDto = roadAddressService.removeDuplicateAddressInfo(roadAddressApiDto);
//        List<RoadAddressDto> roadAddressDtoByPage = roadAddressService.roadAddressByPage(roadAddressDto,page);

        log.info("조회된 결과 수 : {}", roadAddressDto.size());
        return ResponseEntity.status(HttpStatus.OK).body(roadAddressDto);
    }
}

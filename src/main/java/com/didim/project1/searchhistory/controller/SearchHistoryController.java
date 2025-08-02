package com.didim.project1.searchhistory.controller;

import com.didim.project1.common.jwt.CustomUserDetails;
import com.didim.project1.roadaddress.dto.RoadAddressDto;
import com.didim.project1.searchhistory.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;

    @PostMapping("/searchHistory")
    public ResponseEntity<Void> saveSearchHistory(@RequestBody RoadAddressDto roadAddressInfoDto,
                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getId();

        searchHistoryService.saveSearchHistory(roadAddressInfoDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/searchHistory")
    public ResponseEntity<List<RoadAddressDto>> findSearchHistory(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<RoadAddressDto> searchHistoryList = searchHistoryService.findSearchHistory(page, customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(searchHistoryList);
    }
}

package com.didim.project1.searchhistory.service;

import com.didim.project1.roadaddress.dto.RoadAddressDto;
import com.didim.project1.searchhistory.entity.SearchHistory;
import com.didim.project1.searchhistory.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional
    public void saveSearchHistory(RoadAddressDto roadAddressInfo, Long userId) {

        SearchHistory searchHistory = SearchHistory.builder()
                .userId(userId)
                .roadAddress(roadAddressInfo.getRoadAddress())
                .zipcode(roadAddressInfo.getZipcode())
                .buildName(roadAddressInfo.getBuildName())
                .xPos(roadAddressInfo.getXPos())
                .yPos(roadAddressInfo.getYPos())
                .build();
        searchHistoryRepository.save(searchHistory);
    }

    @Transactional(readOnly = true)
    public List<RoadAddressDto> findSearchHistory(int page, Long userId) {
        int offset = (page - 1) * 10;
        return searchHistoryRepository.findAllByPage(offset, userId);
    }

    public Integer countSearchHistory(Long userId) {
        return searchHistoryRepository.count(userId);
    }
}

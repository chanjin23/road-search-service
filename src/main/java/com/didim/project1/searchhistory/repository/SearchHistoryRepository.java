package com.didim.project1.searchhistory.repository;

import com.didim.project1.roadaddress.dto.RoadAddressDto;
import com.didim.project1.searchhistory.entity.SearchHistory;
import com.didim.project1.searchhistory.mapper.SearchHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchHistoryRepository {
    private final SearchHistoryMapper searchHistoryMapper;


    public void save(SearchHistory searchHistory) {
        searchHistoryMapper.save(searchHistory);
    }

    public List<RoadAddressDto> findAllByPage(int page, Long userId) {
        return searchHistoryMapper.findAllByPage(page, userId);
    }

    public Integer count(Long userId) {
        return searchHistoryMapper.count(userId);
    }
}

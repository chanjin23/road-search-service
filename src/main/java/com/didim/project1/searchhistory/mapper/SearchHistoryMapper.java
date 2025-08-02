package com.didim.project1.searchhistory.mapper;

import com.didim.project1.roadaddress.dto.RoadAddressDto;
import com.didim.project1.searchhistory.entity.SearchHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchHistoryMapper {

    void save(SearchHistory searchHistory);

    List<RoadAddressDto> findAllByPage(int page, Long userId);

}

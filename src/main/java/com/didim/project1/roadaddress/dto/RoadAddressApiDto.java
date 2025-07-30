package com.didim.project1.roadaddress.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoadAddressApiDto {

    private String Juso;  // 총 건수
    private PaginationInfo paginationInfo;
    private String category;
    private List<AddressInfoDto> LIST;


    @Getter
    @Builder
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class PaginationInfo {
        private String lastPageNo;
        private String totalPageCount;
        private String firstPageNo;
        private String currentPageNo;
        private String totalRecordCount;
    }

    @Getter
    @Builder
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class AddressInfoDto {
        private double ypos;       // 위도
        private double xpos;       // 경도
        private String JUSO;       // 전체 주소
        private String ZIP_CL;     // 우편번호
        private String BLD_NM;     // 건물명
    }
}

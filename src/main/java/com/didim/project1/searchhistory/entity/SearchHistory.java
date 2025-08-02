package com.didim.project1.searchhistory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistory {
    private Long id;
    private Long userId;
    private String roadAddress;
    private String zipcode;
    private String buildName;
    private double xPos;
    private double yPos;
}

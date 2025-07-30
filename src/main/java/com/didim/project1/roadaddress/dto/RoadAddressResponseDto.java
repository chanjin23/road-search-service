package com.didim.project1.roadaddress.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoadAddressResponseDto {
    private double xPos;
    private double yPos;
    private String roadAddress;
    private String zipcode;
    private String buildName;

}

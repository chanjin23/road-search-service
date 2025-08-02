package com.didim.project1.roadaddress.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoadAddressDto {
    private String roadAddress;
    private String zipcode;
    private String buildName;
    private double xPos;
    private double yPos;

}

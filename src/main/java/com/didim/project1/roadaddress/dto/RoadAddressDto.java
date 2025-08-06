package com.didim.project1.roadaddress.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RoadAddressDto {
    private String roadAddress;
    private String zipcode;
    private String buildName;
    private double xPos;
    private double yPos;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

}

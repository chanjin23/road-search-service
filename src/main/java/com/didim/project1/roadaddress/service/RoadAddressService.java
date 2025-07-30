package com.didim.project1.roadaddress.service;

import com.didim.project1.roadaddress.dto.RoadAddressApiDto;
import com.didim.project1.roadaddress.dto.RoadAddressResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoadAddressService {

    private final RoadAddressComponent roadAddressComponent;
    private final RestTemplate restTemplate;

    // todo 리팩토링
    public List<RoadAddressResponseDto> fetchRoadAddress(String roadAddress) throws URISyntaxException {
        List<RoadAddressResponseDto> roadAddressResponseDtoList = new ArrayList<>();

        URI uri = roadAddressComponent.createUri(roadAddress);

        log.info("요청 API url : {}", uri.toString());

        // todo 예외처리
        RoadAddressApiDto roadAddressApiDto = restTemplate.getForObject(uri, RoadAddressApiDto.class);

        List<RoadAddressApiDto.AddressInfoDto> addressInfoList = roadAddressApiDto.getLIST();

        //중복처리로직
        List<RoadAddressApiDto.AddressInfoDto> addressInfo
                = roadAddressComponent.removeDuplicateAddressInfo(addressInfoList);

        for (RoadAddressApiDto.AddressInfoDto addressInfoDto : addressInfo) {
            RoadAddressResponseDto dto = RoadAddressResponseDto.builder()
                    .yPos(addressInfoDto.getYpos())
                    .xPos(addressInfoDto.getXpos())
                    .roadAddress(addressInfoDto.getJUSO())
                    .zipcode(addressInfoDto.getZIP_CL())
                    .buildName(addressInfoDto.getBLD_NM())
                    .build();

            roadAddressResponseDtoList.add(dto);
        }
        return roadAddressResponseDtoList;
    }
}

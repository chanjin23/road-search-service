package com.didim.project1.roadaddress.service;

import com.didim.project1.roadaddress.dto.RoadAddressApiDto;
import com.didim.project1.roadaddress.dto.RoadAddressDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoadAddressService {

    private final RoadAddressComponent roadAddressComponent;
    private final RestTemplate restTemplate;

    public static final int PAGE_SIZE = 10;                         // 한 페이지에 보여줄 개수

    public RoadAddressApiDto fetchRoadAddress(String roadAddress) throws URISyntaxException {
        URI uri = roadAddressComponent.createUri(roadAddress);

        log.info("요청 API url : {}", uri.toString());

        // todo 예외처리
        return restTemplate.getForObject(uri, RoadAddressApiDto.class);

    }

    public List<RoadAddressDto> removeDuplicateAddressInfo(RoadAddressApiDto roadAddressApiDto) {
        List<RoadAddressDto> roadAddressDtoList = new ArrayList<>();
        List<RoadAddressApiDto.AddressInfoDto> addressInfoList = roadAddressApiDto.getLIST();

        //중복처리로직
        List<RoadAddressApiDto.AddressInfoDto> addressInfo
                = roadAddressComponent.removeDuplicateAddressInfo(addressInfoList);

        for (RoadAddressApiDto.AddressInfoDto addressInfoDto : addressInfo) {
            RoadAddressDto dto = RoadAddressDto.builder()
                    .yPos(addressInfoDto.getYpos())
                    .xPos(addressInfoDto.getXpos())
                    .roadAddress(addressInfoDto.getJUSO())
                    .zipcode(addressInfoDto.getZIP_CL())
                    .buildName(addressInfoDto.getBLD_NM())
                    .build();

            roadAddressDtoList.add(dto);
        }
        return roadAddressDtoList;
    }

    public List<RoadAddressDto> roadAddressByPage(List<RoadAddressDto> roadAddressDto, int page) {
        int fromIndex = (page - 1) * PAGE_SIZE;     // 시작 인덱스
        int toIndex = Math.min(fromIndex + PAGE_SIZE, roadAddressDto.size()); // 끝 인덱스

        if (fromIndex >= roadAddressDto.size() || fromIndex < 0) {
            return Collections.emptyList(); // 요청한 페이지가 존재하지 않으면 빈 리스트 반환
        }

        return roadAddressDto.subList(fromIndex, toIndex);
    }
}

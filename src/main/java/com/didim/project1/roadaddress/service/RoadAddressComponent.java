package com.didim.project1.roadaddress.service;

import com.didim.project1.roadaddress.dto.RoadAddressApiDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.didim.project1.roadaddress.constant.RoadAddressConstants.*;

@Component
@Slf4j
public class RoadAddressComponent {
    @Value("${road-address.api-key}")
    private String apiKey;

    public URI createUri(String roadAddress) throws URISyntaxException {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(
                        ROAD_ADDRESS_END_POINT)
                .queryParam("category", CATEGORY)
                .queryParam("q", roadAddress)
                .queryParam("pageUnit", 100) //100개를 넣는다.
                .queryParam("output", CONTENT_TYPE)
                .queryParam("pageIndex", 1)
                .queryParam("apiKey", apiKey)
                .encode(StandardCharsets.UTF_8);

        return new URI(uriBuilder.build().toUriString());
    }

    public List<RoadAddressApiDto.AddressInfoDto> removeDuplicateAddressInfo(List<RoadAddressApiDto.AddressInfoDto> addressInfoList) {
        Set<String> uniqueSet = new HashSet<>();
        List<RoadAddressApiDto.AddressInfoDto> result = new ArrayList<>();

        for (RoadAddressApiDto.AddressInfoDto info : addressInfoList) {
            String key = info.getJUSO() + "|" + info.getBLD_NM(); // 중복 기준: JUSO + BLD_NM

            if (!uniqueSet.contains(key)) {
                uniqueSet.add(key);
                result.add(info);
            }
        }

        return result;
    }
}

package sion.bestRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sion.bestRoom.dto.DabangRoomDTO;
import sion.bestRoom.feign.DabangFeignClient;
import sion.bestRoom.feign.response.DabangResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DabangService {

    private final DabangFeignClient dabangFeignClient;

    public List<DabangRoomDTO> getDabangRooms() {
        DabangResponse estateTest = dabangFeignClient.getEstateTest();
        log.info("estateTest : {}", estateTest);
        return dabangFeignClient.getEstateTest().getRooms();
    }
}

package sion.bestRoom.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sion.bestRoom.feign.ZigbangFeignClient;
import sion.bestRoom.feign.dto.zigbang.ReqZigbangRoomInfoDTO;
import sion.bestRoom.feign.dto.zigbang.ResZigbangListDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZigbangServiceTest {

    @Autowired
    private ZigbangService zigbangService;

    @Autowired
    ZigbangFeignClient zigbangFeignClient;

    @Test
    @DisplayName("직방에 있는 빌라 가져오기")
    void getZigbangRooms() {
        String zigbangRooms = zigbangService.getZigbangRooms();

//        System.out.println("zigbangRooms = " + zigbangRooms);
        Assertions.assertThat(zigbangRooms).isNotNull();
    }

    @Test
    @DisplayName("직방에 있는 빌라 가져오기")
    void getZigbangFeignClientList() {
        ReqZigbangRoomInfoDTO reqZigbangRoomInfoDTO = new ReqZigbangRoomInfoDTO();
        reqZigbangRoomInfoDTO.setItem_ids(List.of(1L, 2L, 3L));
        ResZigbangListDTO roomInfo = zigbangFeignClient.getRoomInfo(reqZigbangRoomInfoDTO);
        Assertions.assertThat(roomInfo).isNotNull();
    }

}
package sion.bestRoom.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class ZigbangServiceTest {

    @Autowired
    private ZigbangService zigbangService;

    @Test
    @DisplayName("직방에 있는 빌라 가져오기")
    void getZigbangRooms() {
        String zigbangRooms = zigbangService.getZigbangRooms();

        Assertions.assertThat(zigbangRooms).isNotNull();
    }

}
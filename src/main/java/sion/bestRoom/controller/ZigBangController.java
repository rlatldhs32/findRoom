package sion.bestRoom.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sion.bestRoom.service.ZigbangService;
import sion.bestRoom.util.Constants;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/zigbang")
@Slf4j
public class ZigBangController {

    private final ZigbangService zigbangService;

    @Operation(summary = "직방에 있는 빌라 가져오기. ** 주의: 현재 10분쯤 걸림 **")
    @GetMapping("/villas")
    public String getSeoulAreaCode() throws InterruptedException {
        if(Constants.checkZigBangVillaList)
            throw new RuntimeException("이미 직방 방 정보를 가져왔습니다.");
        Constants.checkZigBangVillaList = true;
//        zigbangService.deleteAllVillas();
        return zigbangService.getZigbangRooms();
    }
}

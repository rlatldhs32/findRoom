package sion.bestRoom.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sion.bestRoom.dto.CreateBusDTO;
import sion.bestRoom.dto.CreateSubwayDTO;
import sion.bestRoom.model.City;
import sion.bestRoom.service.BusService;
import sion.bestRoom.service.DabangService;
import sion.bestRoom.service.SubwayService;
import sion.bestRoom.service.ZigbangService;
import sion.bestRoom.util.Constants;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/save")
@Slf4j
public class TestController {

    private final DabangService dabangService;
    @DeleteMapping("/room")
    public void deleteAllRooms() {
        dabangService.deleteByTest(3L);
    }

}

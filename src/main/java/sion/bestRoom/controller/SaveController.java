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
import sion.bestRoom.util.Constants;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/save")
@Slf4j
public class SaveController {

    private final DabangService dabangService;
    private final SubwayService subwayService; //TODO: 모으기
    private final BusService busService;

    @Operation(summary = "서울시 내 구역 code 가져오기")
    @GetMapping("/dabang/area/seoul")
    public List<City> getAllRooms() {
        if(Constants.checkSeoulAreaList)
            throw new RuntimeException("이미 서울시 구역 코드를 가져왔습니다.");
        Constants.checkSeoulAreaList = true;
        dabangService.deleteAllAreas();
        List<City> seoulAreaCode = dabangService.getSeoulAreaCode();
        return seoulAreaCode;
    }

    @Operation(summary = "구역별 모든 정보 가져오기.")
    @GetMapping("/dabang/rooms")
    public List<String> getSeoulAreaCode() throws InterruptedException {
        if(Constants.checkRoomList)
            throw new RuntimeException("이미 방 정보를 가져왔습니다.");
        Constants.checkRoomList = true;
        dabangService.deleteAllRooms();
        List<String> allRoomsInCity = dabangService.getAllRoomsInCity();
        return allRoomsInCity;
    }

    @Operation(summary = "지하철 정보 저장하기.")
    @PostMapping("/subway")
    public List<String> saveSubwayArea(@RequestBody CreateSubwayDTO dto) {
        if(Constants.checkSubwayList)
            throw new RuntimeException("이미 지하철 정보를 가져왔습니다.");
        Constants.checkRoomList = true;
        subwayService.deleteAllSubway();
        List<String> SubwayNameList = subwayService.saveSubway(dto);
        return SubwayNameList;
    }

    @Operation(summary = "버스정류장 정보 저장하기.")
    @PostMapping("/bus")
    public List<String> saveBusArea(@RequestBody CreateBusDTO dto) {
        if(Constants.checkBusList)
            throw new RuntimeException("이미 버스 정보를 가져왔습니다.");
        Constants.checkBusList = true;
        busService.deleteAllBus();
        List<String> busNameList = busService.saveBus(dto);
        return busNameList;
    }
}

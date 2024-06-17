package sion.bestRoom.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sion.bestRoom.dto.CreateBusDTO;
import sion.bestRoom.dto.CreateSubwayDTO;
import sion.bestRoom.model.City;
import sion.bestRoom.service.*;
import sion.bestRoom.util.Constants;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/save")
@Slf4j
public class SaveController {

    private final ZigbangService zigbangService;
    private final RoomService roomService;
    private final SubwayService subwayService; //TODO: 모으기
    private final BusService busService; //TODO: map으로 합치기
    private final CityService cityService;

    @Operation(summary = "직방+다방 방 정보 저장하기. 처음에 수행하면 됨.")
    @GetMapping("/all/rooms")
    public List<String> getZigAndDabangRooms() throws InterruptedException {
        if(Constants.checkRoomList)
            throw new RuntimeException("이미 방 정보를 가져왔습니다.");
        Constants.checkRoomList = true;
        roomService.deleteAllRooms();
        cityService.deleteAllCities();
        List<City> seoulAreaCode = roomService.getSeoulAreaCode();
        List<String> allRoomsInCity = roomService.getDabangRoomsInCity();
        String zigbangRooms = zigbangService.getZigbangRooms();

        return allRoomsInCity;
    }

    @Operation(summary = "서울시 내 구역 code 가져오기 . dabang 룸 저장 전에 시행.")
    @GetMapping("/dabang/area/seoul")
    public List<City> getAllRooms() {
        if(Constants.checkSeoulAreaList)
            throw new RuntimeException("이미 서울시 구역 코드를 가져왔습니다.");
        Constants.checkSeoulAreaList = true;
        cityService.deleteAllCities();
        return roomService.getSeoulAreaCode();
    }

    @Operation(summary = "저장된 도시들의 다방 정보 가져오기.")
    @GetMapping("/dabang/rooms")
    public List<String> getSeoulAreaCode() throws InterruptedException {
        if(Constants.checkRoomList)
            throw new RuntimeException("이미 다방 정보를 가져왔습니다.");
        Constants.checkRoomList = true;
        roomService.deleteAllRooms();
        return roomService.getDabangRoomsInCity();
    }

    @Operation(summary = "지하철 정보 저장하기.")
    @PostMapping("/subway")
    public List<String> saveSubwayArea(@RequestBody CreateSubwayDTO dto) {
        if(Constants.checkSubwayList)
            throw new RuntimeException("이미 지하철 정보를 가져왔습니다.");
        Constants.checkRoomList = true;
        subwayService.deleteAllSubway();
        return subwayService.saveSubway(dto);
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

    @Operation(summary = "직방에 있는 빌라 가져오기. ( 약 2만개 )")
    @GetMapping("/villas")
    public String getZigbangVillas() throws InterruptedException {
        if(Constants.checkZigBangVillaList)
            throw new RuntimeException("이미 직방 방 정보를 가져왔습니다.");
        Constants.checkZigBangVillaList = true;
        zigbangService.deleteZigbangRooms();
        return zigbangService.getZigbangRooms();
    }
}

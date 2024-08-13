package sion.bestRoom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sion.bestRoom.dto.CreateBusDTO;
import sion.bestRoom.dto.CreateSubwayDTO;
import sion.bestRoom.model.City;
import sion.bestRoom.service.*;
import sion.bestRoom.util.Constants;

import java.util.List;


@Transactional
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
    @Transactional
    @GetMapping("/all/rooms")
    public List<String> getZigAndDabangRooms() throws InterruptedException, JsonProcessingException {
        if(Constants.checkRoomList)
            throw new RuntimeException("이미 방 정보를 가져왔습니다.");
        Constants.checkRoomList = true;
        roomService.deleteAllRooms();
        cityService.deleteAllCities();
        List<City> areaCode = roomService.getGeongGiAndSeoulAreaCode();
//        List<String> allRoomsInCity = roomService.getDabangRoomsInCity();
        List<String> allRoomsInCity = roomService.getAllDabangRoomsInCity();
        String zigbangRooms = zigbangService.getZigbangRooms();
        return allRoomsInCity;
    }

//    @Transactional(dontRollbackOn = Exception.class)
//    @Operation(summary = "직방+다방 방 정보 저장하기. 처음에 수행하면 됨.- 오피스텔 버전")
//    @GetMapping("/all/rooms/officetel")
//    public List<String> getZigAndDabangRoomsOffice() throws InterruptedException, JsonProcessingException {
////        if(Constants.checkRoomList)
////            throw new RuntimeException("이미 방 정보를 가져왔습니다.");
//        try {
////        Constants.checkRoomList = true;
//            roomService.deleteAllRooms();
////        cityService.deleteAllCities();
////        List<City> areaCode = roomService.getGeongGiAndSeoulAreaCode();
//            List<String> allRoomsInCity = roomService.getAllDabangRoomsInCity();
//            log.info("Zigbang start");
//        String zigbangRooms = zigbangService.getZigbangRooms();
////        log.info("Zigbang End");
//        }
//        catch (Exception e) {
//            log.error("error : " + e.getMessage());
//        }
//
//        return new ArrayList<>();
//    }


    @Operation(summary = "수도권 내 구역 code 가져오기 . dabang 룸 저장 전에 시행.")
    @GetMapping("/dabang/area")
    public List<City> getAllRooms() {
        if(Constants.checkAreaList)
            throw new RuntimeException("이미 수도권 구역 코드를 가져왔습니다.");
        Constants.checkAreaList = true;
        cityService.deleteAllCities();
        return roomService.getGeongGiAndSeoulAreaCode();
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
    public String saveBusArea() {
        if(Constants.checkBusList)
            throw new RuntimeException("이미 버스 정보를 가져왔습니다.");
        Constants.checkBusList = true;
        busService.deleteAllBus();
        return busService.saveBusByFeignClient();
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

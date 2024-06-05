//package sion.bestRoom.controller;
//
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//import sion.bestRoom.model.City;
//import sion.bestRoom.model.OneRoom;
//import sion.bestRoom.service.DabangService;
//import sion.bestRoom.util.Constants;
//
//import java.util.ArrayList;
//import java.util.List;
//
////@RestController
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1")
//@Slf4j
//public class AllRoomController {
//
//    private final DabangService dabangService;
//
//
//    //x 좌 우 좌표, y 상 하 좌표 받아서 그 사이에 있는 방들을 가져옴.
//    @Operation(summary = "전체 방 조회"
//            , description = "X : 경도 , Y : 위도(y : 위 비슷한어감 ㅎㅎ) x1, x2, y1, y2를 받아서 그 사이에 있는 방들을 가져옴."
//    )
//    @GetMapping("/rooms")
//    public List<OneRoom>  getAllRooms(@Parameter(description = "경도1 : ex) 127.052258761841") @RequestParam(name = "x1") Double x1,
//                                      @Parameter(description = "경도2 : ex) 127.072258761841")@RequestParam(name="x2") Double x2,
//                                      @Parameter(description = "위도1 : ex) 37.2549398021063") @RequestParam(name="y1") Double y1,
//                                      @Parameter(description = "위도2 : ex) 37.5549398021063") @RequestParam(name="y2") Double y2) {
//        List<OneRoom> allRooms = dabangService.getAllRooms(x1, x2, y1, y2);
//        log.info("allRooms : " + allRooms.size());
//        return allRooms;
//    }
//
//
//    @Operation(summary = "가성비 탑 10 방 조회"
//            , description = "X : 경도 , Y : 위도(y : 위 비슷한어감 ㅎㅎ) x1, x2, y1, y2를 받아서 그 사이에 있는 방들을 가져옴."
//    )
//    @GetMapping("/rooms/effective")
//    public List<OneRoom>  getGoodRooms(@Parameter(description = "경도1 : ex) 127.052258761841") @RequestParam(name = "x1") Double x1,
//                                       @Parameter(description = "경도2 : ex) 127.072258761841")@RequestParam(name="x2") Double x2,
//                                       @Parameter(description = "위도1 : ex) 37.2549398021063") @RequestParam(name="y1") Double y1,
//                                       @Parameter(description = "위도2 : ex) 37.5549398021063") @RequestParam(name="y2") Double y2) {
//
//        List<OneRoom> allRooms = dabangService.getAllRooms(x1, x2, y1, y2);
//        allRooms.sort((o1, o2) -> {
//            Double costEffectiveness1 = o1.getTotal_price() / o1.getSize();
//            Double costEffectiveness2 = o2.getTotal_price() / o2.getSize();
//            return costEffectiveness1.compareTo(costEffectiveness2);
//        });
//        return allRooms.subList(0, 10);
//    }
//
//    @Operation(summary = "서울시 내 구역 code 가져오기")
//    @GetMapping("/dabang/area/seoul")
//    public List<City> getAllRooms() {
//        if(Constants.checkSeoulAreaList)
//            throw new RuntimeException("이미 서울시 구역 코드를 가져왔습니다.");
//        Constants.checkSeoulAreaList = true;
//        dabangService.deleteAllAreas();
//        List<City> seoulAreaCode = dabangService.getSeoulAreaCode();
//        return seoulAreaCode;
//    }
//
//    @Operation(summary = "구역별 모든 정보 가져오기.")
//    @GetMapping("/dabang/rooms")
//    public List<String> getSeoulAreaCode() throws InterruptedException {
//        if(Constants.checkRoomList)
//            throw new RuntimeException("이미 방 정보를 가져왔습니다.");
//        Constants.checkRoomList = true;
//        dabangService.deleteAllRooms();
//        List<String> allRoomsInCity = dabangService.getAllRoomsInCity();
//        return allRoomsInCity;
//    }
//
//
//
//
//
//}

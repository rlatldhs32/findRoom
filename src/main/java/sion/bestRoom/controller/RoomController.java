package sion.bestRoom.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sion.bestRoom.model.OneRoom;
import sion.bestRoom.service.RoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
@Slf4j
public class RoomController {

    private final RoomService roomService;


    //x 좌 우 좌표, y 상 하 좌표 받아서 그 사이에 있는 방들을 가져옴.
    @Operation(summary = "전체 방 조회"
            , description = "X : 경도 , Y : 위도(y : 위 비슷한어감 ㅎㅎ) x1, x2, y1, y2를 받아서 그 사이에 있는 방들을 가져옴."
    )
    @GetMapping("")
    public List<OneRoom>  getAllRooms(@Parameter(description = "방 type. 0:월세 , 1:전세 , 2:매매 ") @RequestParam(name = "type",required = false) Integer type,
                                      @Parameter(description = "경도1 : ex) 127.052258761841") @RequestParam(name = "x1") Double x1,
                                      @Parameter(description = "경도2 : ex) 127.072258761841")@RequestParam(name="x2") Double x2,
                                      @Parameter(description = "위도1 : ex) 37.2549398021063") @RequestParam(name="y1") Double y1,
                                      @Parameter(description = "위도2 : ex) 37.5549398021063") @RequestParam(name="y2") Double y2) {
        List<OneRoom> allRooms = roomService.getAllRooms(x1, x2, y1, y2,type);
        log.info("allRooms : " + allRooms.size());
        return allRooms;
    }


    @Operation(summary = "가성비 탑 10 방 조회 number param 없으면 10개 조회"
            , description = "X : 경도 , Y : 위도 x1, x2, y1, y2를 받아서 그 사이에 있는 방들을 가져옴."
    )
    @GetMapping("/effective")
    public List<OneRoom>  getGoodRooms(@Parameter(description = "방 type. 0:월세 , 1:전세 , 2:매매 ") @RequestParam(name = "type",required = false) Integer type,
                                       @Parameter(description = "경도1 : ex) 127.052258761841") @RequestParam(name = "x1") Double x1,
                                       @Parameter(description = "경도2 : ex) 127.072258761841")@RequestParam(name="x2") Double x2,
                                       @Parameter(description = "위도1 : ex) 37.2549398021063") @RequestParam(name="y1") Double y1,
                                       @Parameter(description = "위도2 : ex) 37.5549398021063") @RequestParam(name="y2") Double y2,
                                       @Parameter(description = "TOP 방 갯수") @RequestParam(name="number",required = false, defaultValue = "10") Integer number,
                                       @Parameter(description = "최소 평수") @RequestParam(name="minSize",required = false, defaultValue = "0") Double minSize) {
        return roomService.getBestTopRooms(x1, x2, y1, y2,number,type,minSize);
    }



}

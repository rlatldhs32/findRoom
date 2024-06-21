//package sion.bestRoom.service;
//
//import io.swagger.v3.oas.annotations.Parameter;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.locationtech.jts.io.ParseException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.bind.annotation.RequestParam;
//import sion.bestRoom.model.OneRoom;
//import sion.bestRoom.repository.OneRoomRepository;
//import sion.bestRoom.util.CalculateUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class RoomServiceTest {
//
//    @Autowired
//    private RoomService roomService;
//
//    @Autowired
//    private OneRoomRepository oneRoomRepository;
//
//
//    @Test
//    @DisplayName("hibernate 버전 확인")
//    void hibernateVersion() {
//        System.out.println(org.hibernate.Version.getVersionString());
//    }
//    @Test
//    @DisplayName("getRoomsWithinDistance 테스트")
//    void getRoomsWithinDistance() throws ParseException {
//
//
//        OneRoom oneRoom = OneRoom.builder()
//                .id(3L)
//                .dabang_id("dabang_id")
//                .zigbang_id("zigbang_id")
//                .code("code")
//                .deposit(1000000L)
//                .floor("floor")
//                .img_url("img_url")
//                .maintenance_fee(10000.0)
//                .monthly_rent(100000L)
//                .room_type(1L)
//                .room_type_str("room_type_str")
//                .selling_type(1)
//                .selling_type_str("selling_type_str")
//                .size(100.0)
//                .total_price(1000000.0)
//                .title("title")
//                .y(37.488722)
//                .x(126.783323)
//                .location(CalculateUtil.calculatePoint(126.783323, 37.488722))
//                .build();
//        OneRoom save = oneRoomRepository.save(oneRoom);
//        Assertions.assertThat(save).isNotNull();
////        List<OneRoom> saveRoom = new ArrayList<>();
////        saveRoom.add(oneRoom);
////        int i = oneRoomRepository.saveAllRoom(saveRoom);
////        System.out.println("oneRoom = " + save);
////        Assertions.assertThat(save).isNotNull();
//
////        double lat = 126.783323;
////        double lon = 37.488722;
////        double distance = 1000;
////        List<OneRoom> roomsWithinDistance = roomService.getRoomsWithinDistance(lat, lon, distance);
////        System.out.println("roomsWithinDistance = " + roomsWithinDistance);
////        assertNotNull(roomsWithinDistance);
//    }
//
//    @Test
//    @DisplayName("deleteByZigbangIdIsNotNull 테스트")
//    void deleteByZigbangIdIsNotNull() {
//        double lat = 126.783323;
//        double lon = 37.488722;
//        double distance = 1000;
//        List<OneRoom> roomsWithinDistance = roomService.getRoomsWithinDistance(lat, lon, distance);
//        System.out.println("roomsWithinDistance = " + roomsWithinDistance);
//        assertNotNull(roomsWithinDistance);
//    }
//
//
//    @Test
//    @DisplayName("랭킹찾기 테스트")
//    public void testRank(){
//        // given
//        Optional<OneRoom> byId = oneRoomRepository.findById(413249L);
//        OneRoom oneRoom = byId.get();
//        // when
////        Double costDividedSizeRank = roomService.getCostDividedSizeRank(oneRoom, 2000);
////
////        System.out.println("costDividedSizeRank = " + costDividedSizeRank);
////
////         then
////        Assertions.assertThat(costDividedSizeRank).isNotNull();
//    }
//
//}
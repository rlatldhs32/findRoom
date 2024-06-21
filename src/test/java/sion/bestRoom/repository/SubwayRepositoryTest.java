//package sion.bestRoom.repository;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import sion.bestRoom.dto.SubwayDistanceDTO;
//import sion.bestRoom.model.OneRoom;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class SubwayRepositoryTest {
//
//    @Autowired
//    private SubwayRepository subwayRepository;
//
//    @Autowired
//    private OneRoomRepository oneRoomRepository;
//
//    @Test
//    public void testSubWayDistance() throws Exception {
//        // given
//        List<OneRoom> all = oneRoomRepository.findAll();
//
//        OneRoom oneRoom = all.get(3);
//
//        SubwayDistanceDTO closestSubwayWithDistance = subwayRepository.findClosestSubwayWithDistance(oneRoom.getX(), oneRoom.getY());
//
//        System.out.println("closestSubwayWithDistance = " + closestSubwayWithDistance);
//
//        System.out.println("closestSubwayWithDistance.getDistance() = " + closestSubwayWithDistance.getDistance());
//
//        Assertions.assertThat(closestSubwayWithDistance.getDistance()).isNotNull();
//
//        // when
//
//        // then
//    }
//
//}
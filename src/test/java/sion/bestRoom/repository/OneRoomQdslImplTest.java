//package sion.bestRoom.repository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Transactional
//@SpringBootTest
//class OneRoomQdslImplTest {
//
//    @Autowired
//    EntityManager em;
//
//    @Test
//    @DisplayName("반지층 삭제")
//    void deleteByfloor() {
//        // given
//        OneRoomQdslImpl oneRoomQdsl = new OneRoomQdslImpl(em);
//
//        // when
//        Long count = oneRoomQdsl.deleteByfloor("반지층");
//
//        em.flush();
//        em.clear();
//        // then
//        assertNull(count);
//    }
//}
//package sion.bestRoom.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import sion.bestRoom.model.OneRoom;
//
//import java.util.List;
//
//public interface OneRoomRepository extends JpaRepository<OneRoom, Long> {
//
//     //get OneRoom Between x1 and x2, y1 and y2
//     @Query(value = "SELECT * FROM one_room WHERE (x BETWEEN ?1 AND ?2) AND (y BETWEEN ?3 AND ?4)", nativeQuery = true)
//     List<OneRoom> findBetweenXAndY(Double x1, Double x2, Double y1, Double y2); //3이 작고 4가 큼
//
//        //get OneRoom Between x1 and x2, y1 and y2 and order by total_price/size desc
//        @Query(value = "SELECT * FROM one_room WHERE (x BETWEEN ?1 AND ?2) AND (y BETWEEN ?3 AND ?4) ORDER BY total_price/size DESC", nativeQuery = true)
//     List<OneRoom> findBetweenXAndYOrderByTotalPriceDividedBySizeDesc(Double x1, Double x2, Double y1, Double y2);
//}

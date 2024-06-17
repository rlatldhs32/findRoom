package sion.bestRoom.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sion.bestRoom.model.OneRoom;

import java.util.List;

public interface OneRoomRepository extends JpaRepository<OneRoom, Long> ,OneRoomQdsl{

     //get OneRoom Between x1 and x2, y1 and y2
     @Query(value = "SELECT * FROM one_room WHERE selling_type =?5 AND (x BETWEEN ?1 AND ?2) AND (y BETWEEN ?3 AND ?4)", nativeQuery = true)
     List<OneRoom> findBetweenXAndYAndType(Double x1, Double x2, Double y1, Double y2,Integer type); //3이 작고 4가 큼

    @Query(value = "SELECT * FROM one_room WHERE (x BETWEEN ?1 AND ?2) AND (y BETWEEN ?3 AND ?4)", nativeQuery = true)
    List<OneRoom> findBetweenXAndY(Double x1, Double x2, Double y1, Double y2); //3이 작고 4가 큼

        //get OneRoom Between x1 and x2, y1 and y2 and order by total_price/size desc

    //상위 10개만 가져오기
    @Query(value = "SELECT * FROM one_room WHERE (x BETWEEN ?1 AND ?2) AND (y BETWEEN ?3 AND ?4) ORDER BY total_price/size ASC LIMIT ?5", nativeQuery = true)
     List<OneRoom> findBetweenXAndYOrderByTotalPriceDividedBySizeDesc(Double x1, Double x2, Double y1, Double y2, Integer limit);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM one_room  WHERE one_room.zigbang_id IS NOT NULL", nativeQuery = true)
    void deleteByZigbangIdIsNotNull();


}

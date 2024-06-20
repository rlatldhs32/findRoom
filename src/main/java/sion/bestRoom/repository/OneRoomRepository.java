package sion.bestRoom.repository;

import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sion.bestRoom.model.OneRoom;

import java.util.List;

@Transactional
public interface OneRoomRepository extends JpaRepository<OneRoom, Long> ,OneRoomQdsl{

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM one_room  WHERE one_room.zigbang_id IS NOT NULL", nativeQuery = true)
    void deleteByZigbangIdIsNotNull();

    @Query(value = "SELECT * FROM one_room " +
            "WHERE ST_DISTANCE_SPHERE(location, ST_GeomFromText(?1, 4326)) <= ?2",
            nativeQuery = true)
    List<OneRoom> findRoomsWithinDistance(@Param("wktPoint") String wktPoint, @Param("distance") Double distance);


    @Modifying
    @Query(value = "insert into one_room (title,id,location) " +
            "values (?1, ?2,?3)",
            nativeQuery = true)
    void saveRoom(@Param("title") String title, @Param("id") Long id,@Param("location") String location);


    //findBy

}

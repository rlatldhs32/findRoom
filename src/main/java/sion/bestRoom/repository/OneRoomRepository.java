package sion.bestRoom.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sion.bestRoom.model.OneRoom;

import java.util.List;

public interface OneRoomRepository extends JpaRepository<OneRoom, Long> ,OneRoomQdsl{

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM one_room  WHERE one_room.zigbang_id IS NOT NULL", nativeQuery = true)
    void deleteByZigbangIdIsNotNull();
}

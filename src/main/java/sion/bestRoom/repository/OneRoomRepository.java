package sion.bestRoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sion.bestRoom.model.OneRoom;

public interface OneRoomRepository extends JpaRepository<OneRoom, Long> {
}

package sion.bestRoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sion.bestRoom.model.Bus;
import sion.bestRoom.model.Subway;

public interface BusRepository extends JpaRepository<Bus, Long> {
}

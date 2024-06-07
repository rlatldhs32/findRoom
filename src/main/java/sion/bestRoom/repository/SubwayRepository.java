package sion.bestRoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sion.bestRoom.model.Subway;

public interface SubwayRepository extends JpaRepository<Subway, Long> {
}

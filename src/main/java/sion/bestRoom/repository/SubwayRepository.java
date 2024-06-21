package sion.bestRoom.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sion.bestRoom.dto.SubwayDistanceDTO;
import sion.bestRoom.model.Subway;

public interface SubwayRepository extends JpaRepository<Subway, Long> {

    @Query(value = "SELECT new sion.bestRoom.dto.SubwayDistanceDTO(s, " +
            "CAST(ST_Distance_Sphere(point(?1, ?2), point(s.x, s.y)) AS double)) " +
            "FROM Subway s " +
            "ORDER BY ST_Distance_Sphere(point(?1, ?2), point(s.x, s.y)) ASC " +
            "LIMIT 1")
    SubwayDistanceDTO findClosestSubwayWithDistance(@Param("x") Double x, @Param("y") Double y);
}

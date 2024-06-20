package sion.bestRoom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private Double x;
    private Double y; // 위도 : 37. 머시기

    @Column(columnDefinition = "POINT")
    private Point location;
}

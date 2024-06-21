package sion.bestRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sion.bestRoom.model.Subway;

@Getter
@Setter
@NoArgsConstructor
public class SubwayDistanceDTO {
    private Subway subway;
    private Double distance;

    public SubwayDistanceDTO(Subway subway, Double distance) {
        this.subway = subway;
        this.distance = distance;
    }
}

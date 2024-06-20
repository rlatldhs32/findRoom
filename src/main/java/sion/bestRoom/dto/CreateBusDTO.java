package sion.bestRoom.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusDTO {

    private List<BusInfoDTO> data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusInfoDTO {
        private String 관리도시명;
        private String 정류장번호;
        private String 정류장명;
        private String 경도;
        private String 위도;
    }
}

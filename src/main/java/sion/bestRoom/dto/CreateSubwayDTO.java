package sion.bestRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubwayDTO {

    private List<SubwayInfoDTO> data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubwayInfoDTO {
        private String route;
        private String statn_nm;
        private String crdnt_x;
        private String crdnt_y;
        private String statn_id;
    }
}

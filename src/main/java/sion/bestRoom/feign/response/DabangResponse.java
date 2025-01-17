package sion.bestRoom.feign.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sion.bestRoom.dto.DabangRoomDTO;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DabangResponse {

    private Boolean has_more;
    private Long page;

    private List<DabangRoomDTO> rooms;
}

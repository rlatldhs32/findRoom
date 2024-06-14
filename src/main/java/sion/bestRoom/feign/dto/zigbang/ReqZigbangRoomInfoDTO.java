package sion.bestRoom.feign.dto.zigbang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqZigbangRoomInfoDTO {

    private List<Long> item_ids;
    private String domain="zigbang";
}

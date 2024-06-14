package sion.bestRoom.feign.dto.zigbang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZigbangItemDTO {

    private String lat;
    private String lng;
    private Long itemId;
    private String itemBmType;
}

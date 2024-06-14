package sion.bestRoom.feign.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sion.bestRoom.feign.dto.zigbang.ZigbangItemDTO;
import sion.bestRoom.feign.dto.zigbang.ZigbangSectionDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZigbangResponse {

    private List<ZigbangItemDTO> items;
    private List<ZigbangSectionDTO> sections;
    private List<Object> subways;
}

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
public class ZigbangSectionDTO {

    private String type;
    private List<Long> itemIds;
}

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
public class ZigbangItemDetailDTO {
    private String item_id;
    private List<Double> location;
    private String title;

    private String sales_type; //전세 월세
    private String service_type; // 원룸, 빌라 등
    private String floor; // 층수
    private String building_floor; // 전체 층수
    private Boolean is_first_movein; // 첫입주
    private String images_thumbnail;

    private Long deposit;
    private Long rent;
    private Double size_m2;

    //전용면적, 공급면적까지 따져야함. TODO


    private Double manage_cost;
}

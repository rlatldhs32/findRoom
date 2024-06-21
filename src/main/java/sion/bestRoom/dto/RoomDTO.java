package sion.bestRoom.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private Long id;
    private String dabang_id;
    private String zigbang_id;
    private String title;
    private String floor;
    private Double maintenance_fee; //관리비
    private Long deposit; //보증금
    private Long monthly_rent; //월세
    private Long room_type; //방 종류
    private String room_type_str; //방 종류
    private Double size; // 면적
    private String img_url;
    private Double total_price; // 전세 + 월세의 총 가치 // 전세금 * 전환율(5.8) 나누기 12개월
    private Double x;
    private Double y;
    private String code;
    private Integer selling_type; //0:월세 ,1: 전세  2: 매매
    private String selling_type_str;
    private Double cost_divided_size;
    private Double cost_divided_size_percent;
    private Double subway_distance;


}

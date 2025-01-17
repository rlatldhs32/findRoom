package sion.bestRoom.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DabangRoomDTO {
    private String price_title;
    private Long room_type;
    private String id; // 다방 id
    private String room_type_str;
    private String title;
    private String room_desc2; // 방 설명 // 층 , 면적 , 관리비가 있음 현재 ex ) 16.52m², 관리비 7만"
    private String img_url;
    private Integer selling_type; // 0:월세 , 1: 전세  2: 매매
    private String selling_type_str;

    private List<Double> location;
}

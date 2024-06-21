package sion.bestRoom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class OneRoom{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //가성비 저장
    private Double cost_divided_size;

    private Double cost_divided_size_percent;

    //지하철역까지의 거리
    private Double subway_distance;

    @Column(columnDefinition = "POINT")
    private Point location;


    public void setCostPercent(Double percent) {
        this.cost_divided_size_percent = percent;
    }
    public void setSubway_distance(Double distance) {
        this.subway_distance = distance;
    }
}


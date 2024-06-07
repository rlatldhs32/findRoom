package sion.bestRoom.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {  //기본적인 정보
    private Double x; //위도
    private Double y; //경도

    private Double size; // 면적

    private Long roomType;
    private String roomTypeStr;

}

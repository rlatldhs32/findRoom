package sion.bestRoom.feign.dto.dabang;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DabangLocation {
    Double lat;// 위도
    Double lng; // 경도
}

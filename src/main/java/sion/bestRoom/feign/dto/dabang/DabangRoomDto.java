package sion.bestRoom.feign.dto.dabang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DabangRoomDto {
    private Long seq;
    private String id;
    private String roomTypeName;
    private DabangLocation randomLocation;
    private String complexName;
    private String roomTitle;
    private String roomDesc;
    private String privateTypeName;
    private String priceTitle;
    private List<String> imgUrlList;
    private Boolean isOwner;
    private Boolean isNaverVerify;
}
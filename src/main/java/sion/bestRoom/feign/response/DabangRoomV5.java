package sion.bestRoom.feign.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DabangRoomV5 {
    private long seq;
    private String id;
    private String roomTypeName;
    private Location randomLocation;
    private String complexName;
    private String roomTitle;
    private String roomDesc;
    private String priceTypeName;
    private String priceTitle;
    private List<String> imgUrlList;
    private boolean isExtendUI;
    private boolean isFavorite;
    private boolean isPano;
    private boolean isQuick;
    private boolean isDirect;
    private boolean isOwner;
    private boolean isNaverVerify;
    private String formatNaverVerifyDate;
    private boolean isIconFocus;
    private String iconFocusType;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Location {
        private double lat;
        private double lng;
    }
}
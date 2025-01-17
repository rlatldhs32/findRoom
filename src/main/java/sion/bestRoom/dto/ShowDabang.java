package sion.bestRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowDabang {
    private String imageUrl;
    private Long deposit;

    private Long monthlyRent;

    private String redirectUrl;
    private Double size;

    private Long id;
    private String dabangId;
    private String zigbangId;


    private Double x;
    private Double y;

}
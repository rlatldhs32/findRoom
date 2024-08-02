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
public class DabangV5Result {
    private List<DabangRoomV5> roomList;
    private int total;
    private int limit;
    private Boolean hasMore;
    private int page;
}

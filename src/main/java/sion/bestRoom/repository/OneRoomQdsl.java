package sion.bestRoom.repository;

import sion.bestRoom.dto.RoomDTO;
import sion.bestRoom.model.OneRoom;

import java.util.List;

public interface OneRoomQdsl {

    Long deleteByfloor(String floor);

    List<RoomDTO> findBetweenXAndY(Double x1, Double x2, Double y1, Double y2);


    List<RoomDTO> findBetweenXAndYOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit, Double minSize,Integer sellingType,Long roomType);



    List<RoomDTO> findBetweenXAndYAndTypes(Double x1, Double x2, Double y1, Double y2, Integer sellingType, Long roomType);


    void saveAllIgnore(List<OneRoom> oneRoomList);
}

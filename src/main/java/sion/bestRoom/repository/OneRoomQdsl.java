package sion.bestRoom.repository;

import sion.bestRoom.dto.RoomDTO;

import java.util.List;

public interface OneRoomQdsl {

    Long deleteByfloor(String floor);

    List<RoomDTO> findBetweenXAndY(Double x1, Double x2, Double y1, Double y2);

    List<RoomDTO> findBetweenXAndYAndType(Double x1, Double x2, Double y1, Double y2,Integer type);

    List<RoomDTO> findBetweenXAndYOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit,Double minSize);

    List<RoomDTO> findBetweenXAndYAndTypeOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit, Integer type, Double minSize);


}

package sion.bestRoom.repository;

import sion.bestRoom.model.OneRoom;

import java.util.List;

public interface OneRoomQdsl {

    Long deleteByfloor(String floor);

    List<OneRoom> findBetweenXAndY(Double x1, Double x2, Double y1, Double y2);

    List<OneRoom> findBetweenXAndYAndType(Double x1, Double x2, Double y1, Double y2,Integer type);

    List<OneRoom> findBetweenXAndYOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit,Double minSize);

    List<OneRoom> findBetweenXAndYAndTypeOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit, Integer type,Double minSize);
}

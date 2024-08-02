package sion.bestRoom.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sion.bestRoom.dto.RoomDTO;
import sion.bestRoom.model.OneRoom;

import java.util.List;
import java.util.stream.Collectors;

import static sion.bestRoom.model.QOneRoom.oneRoom;

public class OneRoomQdslImpl implements OneRoomQdsl{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public OneRoomQdslImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long deleteByfloor(String floor) {
        //반지층 삭제
        Long count = queryFactory.delete(oneRoom)
                .where(oneRoom.floor.eq(floor))
                .execute();
        return null;
    }

    @Override
    public List<RoomDTO> findBetweenXAndY(Double x1, Double x2, Double y1, Double y2) {

        return queryFactory.select(Projections.constructor(RoomDTO.class, oneRoom.id , oneRoom.dabang_id, oneRoom.zigbang_id, oneRoom.title, oneRoom.floor, oneRoom.maintenance_fee, oneRoom.deposit, oneRoom.monthly_rent, oneRoom.room_type, oneRoom.room_type_str, oneRoom.size, oneRoom.img_url, oneRoom.total_price, oneRoom.x, oneRoom.y, oneRoom.code, oneRoom.selling_type, oneRoom.selling_type_str, oneRoom.cost_divided_size, oneRoom.cost_divided_size_percent, oneRoom.subway_distance))
                .from(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)))
                .fetch();

    }





    @Override
    public List<RoomDTO> findBetweenXAndYOrderByTotalPriceDividedBySizeDescLimit(
            Double x1, Double x2, Double y1, Double y2, Integer limit, Double minSize,
            Integer sellingType, Long roomType) {

        return queryFactory
                .select(Projections.constructor(RoomDTO.class,
                        oneRoom.id, oneRoom.dabang_id, oneRoom.zigbang_id, oneRoom.title, oneRoom.floor,
                        oneRoom.maintenance_fee, oneRoom.deposit, oneRoom.monthly_rent, oneRoom.room_type,
                        oneRoom.room_type_str, oneRoom.size, oneRoom.img_url, oneRoom.total_price, oneRoom.x,
                        oneRoom.y, oneRoom.code, oneRoom.selling_type, oneRoom.selling_type_str,
                        oneRoom.cost_divided_size, oneRoom.cost_divided_size_percent, oneRoom.subway_distance))
                .from(oneRoom)
                .where(
                        oneRoom.x.between(x1, x2),
                        oneRoom.y.between(y1, y2),
                        oneRoom.size.goe(minSize),
                        sellingTypeEq(sellingType),
                        roomTypeEq(roomType)
                )
                .orderBy(oneRoom.total_price.divide(oneRoom.size).asc())
                .limit(limit)
                .fetch();
    }

    private BooleanExpression sellingTypeEq(Integer sellingType) {
        return sellingType != null ? oneRoom.selling_type.eq(sellingType) : null;
    }

    private BooleanExpression roomTypeEq(Long roomType) {
        return roomType != null ? oneRoom.room_type.eq(roomType) : null;
    }


    @Override
    public List<RoomDTO> findBetweenXAndYAndTypes(Double x1, Double x2, Double y1, Double y2, Integer sellingType, Long roomType) {
        return queryFactory
                .select(Projections.constructor(RoomDTO.class,
                        oneRoom.id, oneRoom.dabang_id, oneRoom.zigbang_id, oneRoom.title, oneRoom.floor,
                        oneRoom.maintenance_fee, oneRoom.deposit, oneRoom.monthly_rent, oneRoom.room_type,
                        oneRoom.room_type_str, oneRoom.size, oneRoom.img_url, oneRoom.total_price, oneRoom.x,
                        oneRoom.y, oneRoom.code, oneRoom.selling_type, oneRoom.selling_type_str,
                        oneRoom.cost_divided_size, oneRoom.cost_divided_size_percent, oneRoom.subway_distance))
                .from(oneRoom)
                .where(
                        oneRoom.x.between(x1, x2),
                        oneRoom.y.between(y1, y2),
                        sellingTypeEq(sellingType),
                        roomTypeEq(roomType)
                )
                .fetch();
    }



    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void saveAllIgnore(List<OneRoom> oneRoomList) {
        // SQL 쿼리 작성
        String sql = "INSERT IGNORE INTO one_room " +
                "(dabang_id, zigbang_id, title, floor, maintenance_fee, deposit, monthly_rent, room_type, room_type_str, " +
                "size, img_url, total_price, x, y, code, selling_type, selling_type_str, cost_divided_size, " +
                "cost_divided_size_percent, subway_distance, location) " +
                "VALUES ";

        // 각 OneRoom 객체에 대해 값을 생성
        String values = oneRoomList.stream()
                .map(room -> String.format("('%s', '%s', '%s', '%s', %f, %d, %d, %d, '%s', %f, '%s', %f, %f, %f, '%s', %d, '%s', %f, %f, %f, POINT(%f, %f))",
                        room.getDabang_id(), room.getZigbang_id(), room.getTitle(), room.getFloor(),
                        room.getMaintenance_fee(), room.getDeposit(), room.getMonthly_rent(), room.getRoom_type(),
                        room.getRoom_type_str(), room.getSize(), room.getImg_url(), room.getTotal_price(),
                        room.getX(), room.getY(), room.getCode(), room.getSelling_type(),
                        room.getSelling_type_str(), room.getCost_divided_size(), room.getCost_divided_size_percent(),
                        room.getSubway_distance(), room.getLocation().getX(), room.getLocation().getY()))
                .collect(Collectors.joining(", "));

        // SQL 실행
        entityManager.createNativeQuery(sql + values).executeUpdate();
    }


}

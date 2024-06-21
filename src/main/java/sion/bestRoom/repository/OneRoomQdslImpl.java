package sion.bestRoom.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import sion.bestRoom.dto.RoomDTO;

import java.util.List;

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
    public List<RoomDTO> findBetweenXAndYAndType(Double x1, Double x2, Double y1, Double y2, Integer type) {
        return queryFactory.select(Projections.constructor(RoomDTO.class, oneRoom.id, oneRoom.dabang_id, oneRoom.zigbang_id, oneRoom.title, oneRoom.floor, oneRoom.maintenance_fee, oneRoom.deposit, oneRoom.monthly_rent, oneRoom.room_type, oneRoom.room_type_str, oneRoom.size, oneRoom.img_url, oneRoom.total_price, oneRoom.x, oneRoom.y, oneRoom.code, oneRoom.selling_type, oneRoom.selling_type_str, oneRoom.cost_divided_size, oneRoom.cost_divided_size_percent, oneRoom.subway_distance))
                .from(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)).and(oneRoom.selling_type.eq(type)))
                .fetch();
    }

    @Override
    public List<RoomDTO> findBetweenXAndYOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit, Double minSize) {
        return queryFactory.select(Projections.constructor(RoomDTO.class, oneRoom.id, oneRoom.dabang_id, oneRoom.zigbang_id, oneRoom.title, oneRoom.floor, oneRoom.maintenance_fee, oneRoom.deposit, oneRoom.monthly_rent, oneRoom.room_type, oneRoom.room_type_str, oneRoom.size, oneRoom.img_url, oneRoom.total_price, oneRoom.x, oneRoom.y, oneRoom.code, oneRoom.selling_type, oneRoom.selling_type_str, oneRoom.cost_divided_size, oneRoom.cost_divided_size_percent, oneRoom.subway_distance))
                .from(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)))
                .where(oneRoom.size.goe(minSize))
                .orderBy(oneRoom.total_price.divide(oneRoom.size).asc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<RoomDTO> findBetweenXAndYAndTypeOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit, Integer type, Double minSize) {

        //one룸이 아닌 RoomDTO로 반환

        return queryFactory.select(Projections.constructor(RoomDTO.class, oneRoom.id, oneRoom.dabang_id, oneRoom.zigbang_id, oneRoom.title, oneRoom.floor, oneRoom.maintenance_fee, oneRoom.deposit, oneRoom.monthly_rent, oneRoom.room_type, oneRoom.room_type_str, oneRoom.size, oneRoom.img_url, oneRoom.total_price, oneRoom.x, oneRoom.y, oneRoom.code, oneRoom.selling_type, oneRoom.selling_type_str, oneRoom.cost_divided_size, oneRoom.cost_divided_size_percent, oneRoom.subway_distance))
                .from(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)).and(oneRoom.selling_type.eq(type)))
                .where(oneRoom.size.goe(minSize))
                .orderBy(oneRoom.total_price.divide(oneRoom.size).asc())
                .limit(limit)
                .fetch();

    }


}

package sion.bestRoom.repository;

import com.querydsl.core.QueryException;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLTemplates;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sion.bestRoom.dto.RoomDTO;
import sion.bestRoom.model.OneRoom;
import sion.bestRoom.model.QOneRoom;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLInsertClause;
import sion.bestRoom.util.CustomException;

import java.sql.Connection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static sion.bestRoom.model.QOneRoom.oneRoom;



@Slf4j
public class OneRoomQdslImpl implements OneRoomQdsl{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

//    private final SQLQueryFactory sqlQueryFactory;


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
                        sellingTypeEq(sellingType),
                        roomTypeEq(roomType),
                        oneRoom.size.goe(minSize)
                )
                .orderBy(oneRoom.cost_divided_size.asc())
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAllIgnore(List<OneRoom> oneRoomList) throws CustomException {
//        public void saveAllIgnore(List<OneRoom> oneRoomList) throws CustomException {
        // SQL 쿼리 작성
        try {
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
        catch (Exception e) {
            log.info("다른 Exception happen! 방 정보 저장 중 오류가 발생했습니다.");
            log.error(e.getMessage());
            throw new CustomException("happen . 쿼리 방 정보 저장 중 오류가 발생했습니다.");
        }
    }

//
//    public void saveAllIgnoreDuplicates(List<OneRoom> oneRooms) {
//
//
//        SQLInsertClause insert = sqlQueryFactory.insert(qOneRoom);
//
//
//
//        for (OneRoom oneRoom : oneRooms) {
//            insert.columns(
//                    qOneRoom.dabang_id,
//                    qOneRoom.zigbang_id,
//                    qOneRoom.title,
//                    qOneRoom.floor,
//                    qOneRoom.maintenance_fee,
//                    qOneRoom.deposit,
//                    qOneRoom.monthly_rent,
//                    qOneRoom.room_type,
//                    qOneRoom.room_type_str,
//                    qOneRoom.size,
//                    qOneRoom.img_url,
//                    qOneRoom.total_price,
//                    qOneRoom.x,
//                    qOneRoom.y,
//                    qOneRoom.code,
//                    qOneRoom.selling_type,
//                    qOneRoom.selling_type_str,
//                    qOneRoom.cost_divided_size,
//                    qOneRoom.cost_divided_size_percent,
//                    qOneRoom.subway_distance,
//                    qOneRoom.location
//            ).values(
//                    oneRoom.getDabang_id(),
//                    oneRoom.getZigbang_id(),
//                    oneRoom.getTitle(),
//                    oneRoom.getFloor(),
//                    oneRoom.getMaintenance_fee(),
//                    oneRoom.getDeposit(),
//                    oneRoom.getMonthly_rent(),
//                    oneRoom.getRoom_type(),
//                    oneRoom.getRoom_type_str(),
//                    oneRoom.getSize(),
//                    oneRoom.getImg_url(),
//                    oneRoom.getTotal_price(),
//                    oneRoom.getX(),
//                    oneRoom.getY(),
//                    oneRoom.getCode(),
//                    oneRoom.getSelling_type(),
//                    oneRoom.getSelling_type_str(),
//                    oneRoom.getCost_divided_size(),
//                    oneRoom.getCost_divided_size_percent(),
//                    oneRoom.getSubway_distance(),
//                    oneRoom.getLocation()
//            ).addBatch();
//        }
//
//        // MySQL specific: convert INSERT into INSERT IGNORE
//        insert.setUseLiterals(true);
//        String sql = insert.toString();
//        sql = sql.replaceFirst("^INSERT", "INSERT IGNORE");
//
//        // Execute the SQL
//        entityManager.createNativeQuery(sql).executeUpdate();
//    }
}

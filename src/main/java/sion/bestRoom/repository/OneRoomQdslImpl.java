package sion.bestRoom.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import sion.bestRoom.model.OneRoom;

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
    public List<OneRoom> findBetweenXAndY(Double x1, Double x2, Double y1, Double y2) {
        return queryFactory.selectFrom(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)))
                .fetch();
    }

    @Override
    public List<OneRoom> findBetweenXAndYAndType(Double x1, Double x2, Double y1, Double y2, Integer type) {
        return queryFactory.selectFrom(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)).and(oneRoom.selling_type.eq(type)))
                .fetch();
    }

    @Override
    public List<OneRoom> findBetweenXAndYOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit, Double minSize) {
        return queryFactory.selectFrom(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)))
                .where(oneRoom.size.goe(minSize))
                .orderBy(oneRoom.total_price.divide(oneRoom.size).asc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<OneRoom> findBetweenXAndYAndTypeOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit, Integer type, Double minSize) {
        return queryFactory.selectFrom(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)).and(oneRoom.selling_type.eq(type)))
                //"반"으로 시작하지 않는
                .where(oneRoom.size.goe(minSize))
                .where(oneRoom.floor.startsWith("반").not())
                .orderBy(oneRoom.total_price.divide(oneRoom.size).asc())
                .limit(limit)
                .fetch();
    }


}

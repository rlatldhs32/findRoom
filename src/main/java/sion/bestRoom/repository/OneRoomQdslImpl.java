package sion.bestRoom.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
    public List<OneRoom> findBetweenXAndYOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit) {
        return queryFactory.selectFrom(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)))
                .orderBy(oneRoom.total_price.divide(oneRoom.size).desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<OneRoom> findBetweenXAndYAndTypeOrderByTotalPriceDividedBySizeDescLimit(Double x1, Double x2, Double y1, Double y2, Integer limit, Integer type) {
        return queryFactory.selectFrom(oneRoom)
                .where(oneRoom.x.between(x1, x2).and(oneRoom.y.between(y1, y2)).and(oneRoom.selling_type.eq(type)))
                .orderBy(oneRoom.total_price.divide(oneRoom.size).desc())
                .limit(limit)
                .fetch();
    }
}

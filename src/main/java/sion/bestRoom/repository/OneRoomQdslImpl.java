package sion.bestRoom.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

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

        queryFactory.delete(oneRoom)
                .where(oneRoom.id.eq(88579L))
                .execute();
        return null;
    }
}

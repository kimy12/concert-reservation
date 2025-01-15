package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.QSeatInfoProjection is a Querydsl Projection type for SeatInfoProjection
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSeatInfoProjection extends ConstructorExpression<SeatInfoProjection> {

    private static final long serialVersionUID = -1784143165L;

    public QSeatInfoProjection(com.querydsl.core.types.Expression<Long> seatId, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<Long> seatNumber, com.querydsl.core.types.Expression<Long> price) {
        super(SeatInfoProjection.class, new Class<?>[]{long.class, java.time.LocalDateTime.class, long.class, long.class}, seatId, createdAt, seatNumber, price);
    }

}


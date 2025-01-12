package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.QAvailableSeatsProjection is a Querydsl Projection type for AvailableSeatsProjection
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAvailableSeatsProjection extends ConstructorExpression<AvailableSeatsProjection> {

    private static final long serialVersionUID = 1560844533L;

    public QAvailableSeatsProjection(com.querydsl.core.types.Expression<Long> concertId, com.querydsl.core.types.Expression<Long> seatId, com.querydsl.core.types.Expression<Long> scheduleId, com.querydsl.core.types.Expression<Long> seatNumber, com.querydsl.core.types.Expression<Long> price) {
        super(AvailableSeatsProjection.class, new Class<?>[]{long.class, long.class, long.class, long.class, long.class}, concertId, seatId, scheduleId, seatNumber, price);
    }

}


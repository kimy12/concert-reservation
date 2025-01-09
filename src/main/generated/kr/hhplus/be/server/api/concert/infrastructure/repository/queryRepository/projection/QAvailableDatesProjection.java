package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.QAvailableDatesProjection is a Querydsl Projection type for AvailableDatesProjection
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAvailableDatesProjection extends ConstructorExpression<AvailableDatesProjection> {

    private static final long serialVersionUID = -771292244L;

    public QAvailableDatesProjection(com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<Long> scheduleId, com.querydsl.core.types.Expression<Long> concertId, com.querydsl.core.types.Expression<Long> seq, com.querydsl.core.types.Expression<java.time.LocalDateTime> startAt) {
        super(AvailableDatesProjection.class, new Class<?>[]{String.class, long.class, long.class, long.class, java.time.LocalDateTime.class}, title, scheduleId, concertId, seq, startAt);
    }

}


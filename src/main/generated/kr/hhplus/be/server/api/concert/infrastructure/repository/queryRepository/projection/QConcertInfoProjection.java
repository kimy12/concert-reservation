package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.QConcertInfoProjection is a Querydsl Projection type for ConcertInfoProjection
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QConcertInfoProjection extends ConstructorExpression<ConcertInfoProjection> {

    private static final long serialVersionUID = 821143170L;

    public QConcertInfoProjection(com.querydsl.core.types.Expression<Long> concertId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<Long> scheduleId, com.querydsl.core.types.Expression<Long> seq, com.querydsl.core.types.Expression<java.time.LocalDateTime> concertSchedule) {
        super(ConcertInfoProjection.class, new Class<?>[]{long.class, String.class, long.class, long.class, java.time.LocalDateTime.class}, concertId, title, scheduleId, seq, concertSchedule);
    }

}


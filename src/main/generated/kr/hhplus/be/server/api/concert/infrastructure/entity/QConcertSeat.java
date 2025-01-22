package kr.hhplus.be.server.api.concert.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConcertSeat is a Querydsl query type for ConcertSeat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConcertSeat extends EntityPathBase<ConcertSeat> {

    private static final long serialVersionUID = 1127525794L;

    public static final QConcertSeat concertSeat = new QConcertSeat("concertSeat");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final NumberPath<Long> seatNumber = createNumber("seatNumber", Long.class);

    public final EnumPath<kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus> status = createEnum("status", kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QConcertSeat(String variable) {
        super(ConcertSeat.class, forVariable(variable));
    }

    public QConcertSeat(Path<? extends ConcertSeat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConcertSeat(PathMetadata metadata) {
        super(ConcertSeat.class, metadata);
    }

}


package kr.hhplus.be.server.api.concert.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConcertSchedule is a Querydsl query type for ConcertSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConcertSchedule extends EntityPathBase<ConcertSchedule> {

    private static final long serialVersionUID = -1182639180L;

    public static final QConcertSchedule concertSchedule = new QConcertSchedule("concertSchedule");

    public final NumberPath<Long> concertId = createNumber("concertId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startAt = createDateTime("startAt", java.time.LocalDateTime.class);

    public QConcertSchedule(String variable) {
        super(ConcertSchedule.class, forVariable(variable));
    }

    public QConcertSchedule(Path<? extends ConcertSchedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConcertSchedule(PathMetadata metadata) {
        super(ConcertSchedule.class, metadata);
    }

}


package kr.hhplus.be.server.api.concert.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConcertInfo is a Querydsl query type for ConcertInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConcertInfo extends EntityPathBase<ConcertInfo> {

    private static final long serialVersionUID = 1127236683L;

    public static final QConcertInfo concertInfo = new QConcertInfo("concertInfo");

    public final DateTimePath<java.time.LocalDateTime> bookingEndAt = createDateTime("bookingEndAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> bookingStartAt = createDateTime("bookingStartAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public QConcertInfo(String variable) {
        super(ConcertInfo.class, forVariable(variable));
    }

    public QConcertInfo(Path<? extends ConcertInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConcertInfo(PathMetadata metadata) {
        super(ConcertInfo.class, metadata);
    }

}


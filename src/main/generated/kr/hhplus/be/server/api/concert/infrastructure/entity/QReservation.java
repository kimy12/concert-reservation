package kr.hhplus.be.server.api.concert.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = -597887069L;

    public static final QReservation reservation = new QReservation("reservation");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final NumberPath<Long> seatId = createNumber("seatId", Long.class);

    public final NumberPath<Long> seatNumber = createNumber("seatNumber", Long.class);

    public final EnumPath<kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus> status = createEnum("status", kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QReservation(String variable) {
        super(Reservation.class, forVariable(variable));
    }

    public QReservation(Path<? extends Reservation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservation(PathMetadata metadata) {
        super(Reservation.class, metadata);
    }

}


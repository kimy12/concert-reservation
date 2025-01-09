package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hhplus.be.server.api.concert.infrastructure.entity.QConcertInfo;
import kr.hhplus.be.server.api.concert.infrastructure.entity.QConcertSchedule;
import kr.hhplus.be.server.api.concert.infrastructure.entity.QConcertSeat;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.AvailableDatesProjection;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.AvailableSeatsProjection;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.ConcertInfoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertInfoQueryRepository  {

    private final JPAQueryFactory queryFactory;

    public List<ConcertInfoProjection> getAllConcertInfos(long concertId) {
        QConcertInfo qConcertInfo = QConcertInfo.concertInfo;
        QConcertSchedule qConcertSchedule = QConcertSchedule.concertSchedule;

        return queryFactory.select(Projections.constructor(ConcertInfoProjection.class,
                qConcertInfo.id,
                qConcertInfo.title,
                qConcertSchedule.id,
                qConcertSchedule.seq,
                qConcertSchedule.startAt))
                .from(qConcertInfo)
                .join(qConcertSchedule).on(qConcertInfo.id.eq(qConcertSchedule.concertId))
                .where(qConcertInfo.id.eq(concertId))
                .fetch();
    }

    public List<AvailableDatesProjection> getAllAvailableDates(long concertId) {
        QConcertInfo qConcertInfo = QConcertInfo.concertInfo;
        QConcertSchedule qConcertSchedule = QConcertSchedule.concertSchedule;
        QConcertSeat qConcertSeat = QConcertSeat.concertSeat;

        return queryFactory.selectDistinct(Projections.fields(AvailableDatesProjection.class,
                        qConcertInfo.title.as("title"),
                        qConcertSchedule.id.as("scheduleId"),
                        qConcertInfo.id.as("concertId"),
                        qConcertSchedule.seq.as("seq"),
                        qConcertSchedule.startAt.as("startAt")))
                .from(qConcertInfo)
                .join(qConcertSchedule).on(qConcertInfo.id.eq(qConcertSchedule.concertId))
                .join(qConcertSeat).on(qConcertSchedule.id.eq(qConcertSeat.scheduleId))
                .where(qConcertInfo.id.eq(concertId), qConcertSeat.status.isNull())
                .fetch();
    }

    public List<AvailableSeatsProjection> getAllAvailableSeats(long scheduleId) {
        QConcertSchedule qConcertSchedule = QConcertSchedule.concertSchedule;
        QConcertSeat qConcertSeat = QConcertSeat.concertSeat;

        return queryFactory.select(Projections.fields(AvailableSeatsProjection.class,
                        qConcertSchedule.concertId.as("concertId"),
                        qConcertSeat.id.as("seatId"),
                        qConcertSchedule.id.as("scheduleId"),
                        qConcertSeat.seatNumber.as("seatNumber"),
                        qConcertSeat.price.as("price")))
                .from(qConcertSeat)
                .join(qConcertSchedule).on(qConcertSeat.scheduleId.eq(qConcertSchedule.id))
                .where(qConcertSeat.scheduleId.eq(scheduleId)
                        ,qConcertSeat.status.isNull())
                .fetch();
    }

}

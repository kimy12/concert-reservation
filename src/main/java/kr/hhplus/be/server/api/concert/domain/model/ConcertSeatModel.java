package kr.hhplus.be.server.api.concert.domain.model;

import jakarta.persistence.Version;
import kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ConcertSeatModel {
    private long seatId;

    private long scheduleId;

    private long concertId;

    private long price;

    private long version;

    private long seatNumber;

    ConcertSeatStatus status;

    // reservation 의 createdAt
    private LocalDateTime createdAt;

    @Builder
    public ConcertSeatModel(long seatId,long version, long scheduleId, long concertId, long price, long seatNumber, ConcertSeatStatus status, LocalDateTime createdAt) {
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.concertId = concertId;
        this.price = price;
        this.seatNumber = seatNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.version = version;
    }

    public ConcertSeat toEntity() {
        return ConcertSeat.builder()
                .id(seatId)
                .scheduleId(scheduleId)
                .seatNumber(seatNumber)
                .price(price)
                .version(version)
                .status(status)
                .build();
    }

    public ConcertResponse.SeatInfo toResponseDto (){
        return ConcertResponse.SeatInfo.builder()
                .scheduleId(this.scheduleId)
                .concertId(this.concertId)
                .seatId(this.seatId)
                .seatNumber(this.seatNumber)
                .price(this.price)
                .build();
    }

    public void turnToPending (){
        this.status = ConcertSeatStatus.PENDING;
    }

    public void turnToVoided (){
        this.status = ConcertSeatStatus.VOIDED;
    }

    public boolean checkCreatedAt (LocalDateTime now) {
        LocalDateTime expiredAt = this.createdAt.plusMinutes(5);
        return expiredAt.isBefore(now);
    }
}

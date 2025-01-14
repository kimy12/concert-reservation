package kr.hhplus.be.server.api.concert.domain.model;

import kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConcertSeatModel {
    private long seatId;

    private long scheduleId;

    private long concertId;

    private long price;

    private long seatNumber;

    ConcertSeatStatus status;

    @Builder
    public ConcertSeatModel(long seatId, long scheduleId, long concertId, long price, long seatNumber, ConcertSeatStatus status) {
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.concertId = concertId;
        this.price = price;
        this.seatNumber = seatNumber;
        this.status = status;
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
}

package kr.hhplus.be.server.api.concert.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class ConcertSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scheduleId; // fk

    private Long seatNumber;

    private Long price;

    @Enumerated(EnumType.STRING)
    ConcertSeatStatus status;

    @Builder
    public ConcertSeat(Long id, Long scheduleId, Long seatNumber, Long price, ConcertSeatStatus status) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }

    public ConcertSeatModel toModel() {
        return ConcertSeatModel.builder()
                .seatId(this.id)
                .concertId(this.scheduleId)
                .scheduleId(this.scheduleId)
                .seatNumber(this.seatNumber)
                .price(this.price)
                .status(this.status)
                .build();
    }
}

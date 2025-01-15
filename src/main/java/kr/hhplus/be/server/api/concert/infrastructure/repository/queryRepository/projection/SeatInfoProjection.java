package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection;

import com.querydsl.core.annotations.QueryProjection;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SeatInfoProjection {

    private long seatId;

    private LocalDateTime createdAt;

    private long seatNumber;

    private long price;

    @QueryProjection
    public SeatInfoProjection(long seatId, LocalDateTime createdAt, long seatNumber, long price) {
        this.seatId = seatId;
        this.createdAt = createdAt;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public ConcertSeatModel toModel(){
        return ConcertSeatModel.builder()
                .seatId(this.seatId)
                .createdAt(this.createdAt)
                .seatNumber(this.seatNumber)
                .price(this.price)
                .build();
    }
}

package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection;

import com.querydsl.core.annotations.QueryProjection;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AvailableSeatsProjection {
    private Long concertId;
    private Long seatId;
    private Long scheduleId;
    private Long seatNumber;
    private Long price;

    @QueryProjection
    public AvailableSeatsProjection(Long concertId, Long seatId, Long scheduleId, Long seatNumber, Long price) {
        this.concertId = concertId;
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public ConcertSeatModel toDto(){
        return ConcertSeatModel.builder()
                .scheduleId(this.scheduleId)
                .seatId(this.seatId)
                .concertId(this.concertId)
                .seatNumber(this.seatNumber)
                .price(this.price)
                .build();
    }
}

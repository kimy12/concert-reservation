package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection;

import com.querydsl.core.annotations.QueryProjection;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AvailableDatesProjection {

    private String title;

    private Long scheduleId;

    private Long concertId; // fk

    private Long seq;

    LocalDateTime startAt;

    @QueryProjection
    public AvailableDatesProjection(String title, Long scheduleId, Long concertId, Long seq, LocalDateTime startAt) {
        this.title = title;
        this.scheduleId = scheduleId;
        this.concertId = concertId;
        this.seq = seq;
        this.startAt = startAt;
    }

    public ConcertScheduleModel toModel() {
        return ConcertScheduleModel.builder()
                .title(this.title)
                .scheduleId(this.scheduleId)
                .concertId(this.concertId)
                .seq(this.seq)
                .startAt(this.startAt)
                .build();
    }
}

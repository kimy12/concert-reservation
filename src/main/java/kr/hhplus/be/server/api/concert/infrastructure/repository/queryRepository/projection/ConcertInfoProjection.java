package kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection;

import com.querydsl.core.annotations.QueryProjection;
import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
@NoArgsConstructor
public class ConcertInfoProjection {

    private long concertId;
    private String title;
    private long scheduleId;
    private long seq;
    private LocalDateTime concertSchedule;

    @QueryProjection
    public ConcertInfoProjection(long concertId, String title, long scheduleId, long seq, LocalDateTime concertSchedule) {
        this.concertId = concertId;
        this.title = title;
        this.scheduleId = scheduleId;
        this.seq = seq;
        this.concertSchedule = concertSchedule;
    }

    public ConcertInfoModel toModel(){
        return ConcertInfoModel.builder()
                .concertId(this.concertId)
                .title(this.title)
                .scheduleId(this.scheduleId)
                .seq(this.seq)
                .concertSchedule(this.concertSchedule)
                .build();
    }
}

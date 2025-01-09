package kr.hhplus.be.server.api.concert.domain.dto;

import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ConcertInfoDto {

    private long concertId;
    private String title;
    private long scheduleId;
    private long seq;
    private LocalDateTime concertSchedule;

    @Builder
    public ConcertInfoDto(long concertId, String title, long scheduleId, long seq, LocalDateTime concertSchedule) {
        this.concertId = concertId;
        this.title = title;
        this.scheduleId = scheduleId;
        this.seq = seq;
        this.concertSchedule = concertSchedule;
    }

    public ConcertResponse.ConcertInfo toResponseDto(){
        return ConcertResponse.ConcertInfo
                .builder()
                .concertId(this.concertId)
                .title(this.title)
                .scheduleId(this.scheduleId)
                .seq(this.seq)
                .concertSchedule(this.concertSchedule)
                .build();
    }
}

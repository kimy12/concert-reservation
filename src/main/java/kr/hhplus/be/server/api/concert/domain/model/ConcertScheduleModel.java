package kr.hhplus.be.server.api.concert.domain.model;

import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ConcertScheduleModel {

    private String title;

    private long scheduleId;

    private long concertId; // fk

    private long seq;

    LocalDateTime startAt;

    public ConcertResponse.AvailableDates toResponseDto(){
        return ConcertResponse.AvailableDates.builder()
                .seq(this.seq)
                .title(this.title)
                .scheduleId(this.scheduleId)
                .availableDate(this.startAt)
                .build();
    }

    @Builder
    public ConcertScheduleModel(long scheduleId, String title, long concertId, long seq, LocalDateTime startAt) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.concertId = concertId;
        this.seq = seq;
        this.startAt = startAt;
    }
}

package kr.hhplus.be.server.api.concert.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.hibernate.query.criteria.JpaSearchedCase;

import java.time.LocalDateTime;
import java.util.List;

public class ConcertResponse {

    @Builder
    public record ConcertInfo (
            long concertId,
            String title,
            long scheduleId,
            long seq,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime concertSchedule
    ) {}

    @Builder
    public record AvailableDates (
            long concertId,
            String title,
            long seq,
            long scheduleId,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime availableDate
    ){}

    @Builder
    public record SeatInfo (
            long concertId,
            long seatId,
            long scheduleId,
            long seatNumber,
            long price
    ){
    }

    @Builder
    public record ReservedSeatInfo (
            long scheduleId,
            long seatNumber,
            long price,
            LocalDateTime createAt
    ){
    }

}

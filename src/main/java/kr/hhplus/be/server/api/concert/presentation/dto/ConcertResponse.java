package kr.hhplus.be.server.api.concert.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ConcertResponse {

    @Builder
    public record ConcertInfo (
            long concertId,
            String title,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime startAt) {
    }

    public record AvailableDates (
            long concertId,
            
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
            List<LocalDateTime> availableDates) {
    }

    public record SeatInfo (
            long concertId,
            List<Integer> seatNumber,
            long price
    ){
    }

    public record ReservedSeatInfo (
            long concertId,
            long seatNumber,
            long price
    ){
    }

}

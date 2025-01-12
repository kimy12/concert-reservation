package kr.hhplus.be.server.api.concert.presentation.dto;

public class ConcertRequest {
    public record ReserveConcert(
            long userId,
            long scheduleId,
            long seatId
    ) {
    }
}

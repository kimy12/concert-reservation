package kr.hhplus.be.server.api.concert.application.event.listener;

public record ReservationEventListener(long userId,
                                       long scheduleId,
                                       long seatNumber,
                                       long price) {
}

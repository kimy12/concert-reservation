package kr.hhplus.be.server.api.concert.application.event;

public record AfterReservationConfirmedEvent(long userId,
                                             long scheduleId,
                                             long seatNumber,
                                             long price) {
}

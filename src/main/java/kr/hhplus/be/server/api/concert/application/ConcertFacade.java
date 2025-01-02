package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertFacade {
    public ConcertResponse.ConcertInfo getAvailableConcert(String title) {
        return null;
    }

    public ConcertResponse reserveSeat(ConcertRequest.ReserveConcert request) {
        return null;
    }

    public ConcertResponse.AvailableDates getAvailableDates(long concertId) {
        return null;
    }

    public List<ConcertResponse.SeatInfo> getAvailableSeats(long concertId) {
        return null;
    }
}

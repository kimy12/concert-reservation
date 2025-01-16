package kr.hhplus.be.server.api.concert.presentation.controller;

import kr.hhplus.be.server.api.common.response.RestResponse;
import kr.hhplus.be.server.api.concert.application.ConcertFacade;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/concerts")
public class ConcertController implements SwaggerApi{

    private final ConcertFacade concertFacade;

    /**
     * 콘서트 검색
     *
     */
    @Override
    @GetMapping("/api/v1")
    public RestResponse<List<ConcertResponse.ConcertInfo>> concertInfo(@RequestParam(name = "concertId") long concertId) {
        return RestResponse.ok(concertFacade.getConcertInfo(concertId));
    }

    /**
     * 예약 가능한 날짜를 가져온다.
     */
    @Override
    @GetMapping("/api/v1/{concertId}/availableDates")
    public RestResponse<List<ConcertResponse.AvailableDates>> availableDates(@PathVariable(name = "concertId") long concertId) {
        return RestResponse.ok(concertFacade.getAvailableDates(concertId));
    }

    /**
     * 예약 가능한 좌석을 가져온다.
     */
    @Override
    @GetMapping("/api/v1/{scheduleId}/availableSeats")
    public RestResponse<List<ConcertResponse.SeatInfo>> availableSeats(@PathVariable(name = "scheduleId") long scheduleId) {
        return RestResponse.ok(concertFacade.getAvailableSeats(scheduleId));
    }

    /**
     * 콘서트 자리를 예약한다.
     *
     */
    @Override
    @PostMapping("/api/v1/reserveSeat")
    public RestResponse<ConcertResponse.ReservedSeatInfo> reserveConcertSeat (@RequestBody ConcertRequest.ReserveConcert request) {
        return RestResponse.ok(concertFacade.reservedSeat(request));
    }

    /**
     *
     * 예약 좌석을 결제한다.
     */
    @Override
    @PatchMapping("/api/v1/paySeat")
    public RestResponse<ConcertResponse.ReservedSeatInfo> payConcertSeat (@RequestBody ConcertRequest.PayReserveConcert request){
        return RestResponse.ok(concertFacade.payReservedSeat(request));
    }
}

package kr.hhplus.be.server.api.concert.presentation.controller;

import kr.hhplus.be.server.api.common.response.ApiResponse;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/concerts")
public class ConcertController {

    /**
     * 콘서트 검색
     *
     */
    @GetMapping("/api/v1")
    ApiResponse<ConcertResponse.ConcertInfo> concertList(@RequestParam(name = "title") String title) {

        return ApiResponse.ok(new ConcertResponse.ConcertInfo(1, "콘서트", LocalDateTime.of(2024, 5, 5, 14, 0)));
    }

    /**
     * 예약 가능한 날짜를 가져온다.
     */
    @GetMapping("/api/v1/{concertId}/availableDates")
    ApiResponse<ConcertResponse.AvailableDates> availableDates(@PathVariable(name = "concertId") long concertId) {

        return ApiResponse.ok(new ConcertResponse.AvailableDates(1, List.of(LocalDateTime.of(2024, 5, 5, 14, 0), LocalDateTime.of(2024, 5, 6, 14, 0))));
    }

    /**
     * 예약 가능한 좌석을 가져온다.
     */
    @GetMapping("/api/v1/{concertId}/availableSeats/{date}")
    ApiResponse<ConcertResponse.SeatInfo> availableSeats(@PathVariable(name = "concertId") long concertId,
                                                         @PathVariable(name = "date") LocalDateTime date) {

        return ApiResponse.ok(new ConcertResponse.SeatInfo(1, List.of(1,2,3,20), 5000));
    }

    /**
     * 콘서트 자리를 예약한다.
     *
     */
    @PostMapping("/api/v1/reserveSeat")
    ApiResponse<ConcertResponse.ReservedSeatInfo> reserveConcertSeat (@RequestBody ConcertRequest.ReserveConcert request) {

        return ApiResponse.ok(new ConcertResponse.ReservedSeatInfo(1, 3, 5000));
    }
}

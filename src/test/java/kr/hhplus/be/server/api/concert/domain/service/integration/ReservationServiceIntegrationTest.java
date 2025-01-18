package kr.hhplus.be.server.api.concert.domain.service.integration;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.repository.ReservationRepository;
import kr.hhplus.be.server.api.concert.domain.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus.RESERVING;
import static kr.hhplus.be.server.api.concert.domain.enums.error.SeatErrorCode.SEAT_NOT_AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
public class ReservationServiceIntegrationTest {

    @Autowired
    protected ReservationService reservationService;

    @Autowired
    protected ReservationRepository reservationRepository;

    @DisplayName("자리를 예약한다.")
    @Test
    void reserveSeat() {
        // given
        long userId = 1L;
        ConcertSeatModel seatInfo = ConcertSeatModel.builder()
                .seatId(15L)
                .price(5000L)
                .seatNumber(15L)
                .status(ConcertSeatStatus.PENDING)
                .scheduleId(15L)
                .build();

        // when
        ReservationModel reservedSeat = reservationService.reserveSeat(userId, seatInfo);

        // then
        assertThat(reservedSeat).isNotNull();
        assertThat(reservedSeat.getSeatNumber()).isEqualTo(15L);
        assertThat(reservedSeat.getStatus()).isEqualTo(RESERVING);
        assertThat(reservedSeat.getScheduleId()).isEqualTo(15L);
        assertThat(reservedSeat.getPrice()).isEqualTo(5000L);
    }

    @DisplayName("예약한 좌석을 조회한다.")
    @Test
    void findByReservedId() {
        // given
        long userId = 2L;
        ConcertSeatModel seatInfo = ConcertSeatModel.builder()
                .seatId(16L)
                .price(5000L)
                .seatNumber(16L)
                .status(ConcertSeatStatus.PENDING)
                .scheduleId(15L)
                .build();
        ReservationModel reservedSeat = reservationService.reserveSeat(userId, seatInfo);
        LocalDateTime now = reservedSeat.getCreatedAt().plusMinutes(3);

        // when
        ReservationModel reserved = reservationService.findByReservedId(reservedSeat.getId(), now);

        // then
        assertThat(reserved).isNotNull();
        assertThat(reserved.getSeatNumber()).isEqualTo(16L);
        assertThat(reserved.getStatus()).isEqualTo(RESERVING);
        assertThat(reserved.getScheduleId()).isEqualTo(15L);
        assertThat(reserved.getPrice()).isEqualTo(5000L);
    }

    @DisplayName("잘못된 예약번호로 예약을 조회하면 예외를 발생한다.")
    @Test
    void findByReservedIdWithInvalidId() {
        // given
        long reservedId = 100L;
        LocalDateTime now = LocalDateTime.now();

        // when // then
        assertThatThrownBy(() -> reservationService.findByReservedId(reservedId, now))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(SEAT_NOT_AVAILABLE.getMessage());
    }
}

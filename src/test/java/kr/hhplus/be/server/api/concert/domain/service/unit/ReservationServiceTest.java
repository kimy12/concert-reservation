package kr.hhplus.be.server.api.concert.domain.service.unit;

import kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.repository.ReservationRepository;
import kr.hhplus.be.server.api.concert.domain.service.ReservationService;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @DisplayName("좌석을 선점한다.")
    @Test
    void reserveSeat() {
        // given
        Long userId = 1L;
        ReservationModel reservationModel =
                ReservationModel.builder()
                        .id(1L)
                        .userId(userId)
                        .seatId(1L)
                        .seatNumber(1L)
                        .price(5000L)
                        .createdAt(LocalDateTime.now())
                        .status(ReservationStatus.RESERVING)
                        .build();

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationModel);
        ConcertSeatModel param = ConcertSeatModel.builder()
                                                    .seatId(userId)
                                                    .scheduleId(1L)
                                                    .concertId(1L)
                                                    .price(5000L)
                                                    .seatNumber(1L)
                                                    .status(null)
                                                    .build();


        // when
        ReservationModel result = reservationService.reserveSeat(userId, param);

        // then
        assertThat(result).isEqualTo(reservationModel);
    }

}
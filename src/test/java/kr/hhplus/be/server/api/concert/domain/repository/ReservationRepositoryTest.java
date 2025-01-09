package kr.hhplus.be.server.api.concert.domain.repository;

import kr.hhplus.be.server.api.concert.domain.dto.ReservationDto;
import kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ReservationJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    @DisplayName("예약을 저장한다.")
    @Test
    void saveReservation() {
        // given
        Reservation reservation = Reservation.builder()
                .userId(1L)
                .seatId(2L)
                .seatNumber(3L)
                .scheduleId(4L)
                .status(ReservationStatus.RESERVING)
                .price(5000L)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        ReservationDto savedDto = reservationRepository.save(reservation);

        // then
        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getUserId()).isEqualTo(1L);
        assertThat(savedDto.getSeatId()).isEqualTo(2L);
        assertThat(savedDto.getSeatNumber()).isEqualTo(3L);
        assertThat(savedDto.getScheduleId()).isEqualTo(4L);
        assertThat(savedDto.getStatus()).isEqualTo(ReservationStatus.RESERVING);
        assertThat(savedDto.getPrice()).isEqualTo(5000L);
    }

    @DisplayName("좌석 ID로 예약을 조회한다.")
    @Test
    void findReservationBySeatId() {
        // given
        Reservation reservation = Reservation.builder()
                .userId(1L)
                .seatId(2L)
                .seatNumber(3L)
                .scheduleId(4L)
                .status(ReservationStatus.RESERVING)
                .price(5000L)
                .createdAt(LocalDateTime.now())
                .build();

        ReservationDto savedDto = reservationRepository.save(reservation);

        // when
        Optional<ReservationDto> foundDto = reservationRepository.findBySeatId(2L);

        // then
        assertThat(foundDto).isPresent();
        assertThat(foundDto.get().getUserId()).isEqualTo(savedDto.getUserId());
        assertThat(foundDto.get().getSeatId()).isEqualTo(savedDto.getSeatId());
    }
}
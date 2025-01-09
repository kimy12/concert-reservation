package kr.hhplus.be.server.api.concert.domain.repository;

import kr.hhplus.be.server.api.concert.domain.dto.ConcertInfoDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertScheduleDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertSeatDto;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertInfo;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSchedule;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ConcertInfoJpaRepository;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ConcertScheduleJpaRepository;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ConcertSeatJpaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConcertRepositoryTest {

    @Autowired
    private ConcertRepository repository;

    @Autowired
    private ConcertInfoJpaRepository jpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository scheduleJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository seatJpaRepository;

    @AfterEach
    void tearDown() {
        seatJpaRepository.deleteAllInBatch();
        scheduleJpaRepository.deleteAllInBatch();
        jpaRepository.deleteAllInBatch();
    }

    @DisplayName("콘서트 id로 콘서트 정보를 가져온다.")
    @Test
    @Order(2)
    void getConcertInfoById() {
        // given
        ConcertInfo concert1 = getConcertInfo("콘서트1", LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 2, 1, 1, 0));

        long concertId = jpaRepository.save(concert1).getId();

        ConcertSchedule schedule1 = getSchedule(concertId, 1L, LocalDateTime.of(2025, 5, 1, 0, 0));
        ConcertSchedule schedule2 = getSchedule(concertId, 2L, LocalDateTime.of(2025, 5, 2, 0, 0));
        scheduleJpaRepository.saveAll(List.of(schedule1, schedule2));

        // when
        List<ConcertInfoDto> concertInfo = repository.getConcertInfo(concertId);

        // then
        assertThat(concertInfo).hasSize(2);
        assertThat(concertInfo).extracting("concertId","title", "seq", "concertSchedule")
                .containsExactlyInAnyOrder(
                    tuple(concertId,"콘서트1", 1L, LocalDateTime.of(2025, 5, 1, 0, 0)),
                    tuple(concertId,"콘서트1", 2L, LocalDateTime.of(2025, 5, 2, 0, 0))
                );
    }

    @DisplayName("콘서트 id로 콘서트 예약가능한 날짜 정보를 가져온다.")
    @Test
    @Order(1)
    void getAvailableDateInfoInfoById() {
        // given
        ConcertInfo concert1 = getConcertInfo("콘서트1", LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 2, 1, 1, 0));

        ConcertInfo concertInfo = jpaRepository.save(concert1);

        ConcertSchedule schedule1 = getSchedule(concertInfo.getId(), 1L, LocalDateTime.of(2025, 5, 1, 0, 0));
        ConcertSchedule schedule2 = getSchedule(concertInfo.getId(), 2L, LocalDateTime.of(2025, 5, 2, 0, 0));
        scheduleJpaRepository.saveAll(List.of(schedule1, schedule2));


        ConcertSeat seat1 = ConcertSeat.builder()
                .scheduleId(schedule1.getId())
                .seatNumber(1L)
                .price(5000L)
                .status(PENDING)
                .build();

        ConcertSeat seat2 = ConcertSeat.builder()
                .scheduleId(schedule1.getId())
                .seatNumber(2L)
                .price(5000L)
                .build();

        ConcertSeat seat3 = ConcertSeat.builder()
                .scheduleId(schedule2.getId())
                .seatNumber(3L)
                .price(10000L)
                .build();

        seatJpaRepository.saveAll(List.of(seat1, seat2, seat3));

        // when
        List<ConcertScheduleDto> availableDates = repository.getAvailableDates(schedule1.getId());

        // then
        assertThat(availableDates).hasSize(2);
        assertThat(availableDates).extracting("title","scheduleId","concertId", "seq","startAt")
                .containsExactlyInAnyOrder(
                        tuple(concert1.getTitle(),schedule1.getId(), concertInfo.getId(),schedule1.getSeq(), schedule1.getStartAt()),
                        tuple(concert1.getTitle(),schedule2.getId(), concertInfo.getId(),schedule2.getSeq(), schedule2.getStartAt())
                );
    }

    @DisplayName("콘서트 스케줄 id로 콘서트 예약가능한 좌석 정보를 가져온다.")
    @Test
    @Order(3)
    void getAvailableSeatInfoInfoById() {
        // given
        ConcertSchedule schedule1 = getSchedule(5L, 5L, LocalDateTime.of(2025, 5, 1, 0, 0));
        scheduleJpaRepository.save(schedule1);


        ConcertSeat seat1 = ConcertSeat.builder()
                .scheduleId(schedule1.getId())
                .seatNumber(1L)
                .price(5000L)
                .status(PENDING) // NOT NULL
                .build();

        ConcertSeat seat2 = ConcertSeat.builder()
                .scheduleId(schedule1.getId())
                .seatNumber(2L)
                .price(5000L)
                .status(null)
                .build(); // NULL

        ConcertSeat seat3 = ConcertSeat.builder()
                .scheduleId(schedule1.getId())
                .seatNumber(3L)
                .price(10000L)
                .status(null)
                .build(); // NULL


        seatJpaRepository.saveAll(List.of(seat1, seat2, seat3));

        // when
        List<ConcertSeatDto> availableSeats = repository.getAvailableSeats(schedule1.getId());

        // then
        assertThat(availableSeats).hasSize(2);
        assertThat(availableSeats).extracting("concertId","seatId","scheduleId", "seatNumber","price")
                .containsExactlyInAnyOrder(
                        tuple(schedule1.getConcertId(), seat2.getId(), schedule1.getId(),seat2.getSeatNumber(), seat2.getPrice()),
                        tuple(schedule1.getConcertId(), seat3.getId(), schedule1.getId(),seat3.getSeatNumber(), seat3.getPrice())
                );
    }

    private static ConcertInfo getConcertInfo(String title, LocalDateTime start, LocalDateTime end) {
        return ConcertInfo.builder()
                .title(title)
                .bookingStartAt(start)
                .bookingEndAt(end)
                .build();
    }

    private static ConcertSchedule getSchedule(long concertId, Long seq, LocalDateTime startAt) {
        return ConcertSchedule.builder()
                .concertId(concertId)
                .seq(seq)
                .startAt(startAt)
                .build();
    }

}
package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus;
import kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ConcertSeatJpaRepository;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ReservationJpaRepository;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import kr.hhplus.be.server.api.point.domain.entity.UserPoint;
import kr.hhplus.be.server.api.point.domain.repository.UserPointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcertFacadeIntegrationTest {

    @Autowired
    private ConcertFacade concertFacade;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    @Autowired
    private UserPointRepository userPointRepository;

    @DisplayName("좌석 선점 동시성 테스트")
    @Test
    void reservedSeat() throws InterruptedException {
        // given
        long scheduleId = 10L;
        int memberCount = 40;
        ConcertSeat concertSeat = ConcertSeat.builder()
                .scheduleId(scheduleId)
                .seatNumber(1L)
                .price(5000L)
                .status(ConcertSeatStatus.VOIDED)
                .build();
        ConcertSeat saved = concertSeatJpaRepository.save(concertSeat);


        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch latch = new CountDownLatch(memberCount);
        // when
        for (int i = 0; i < memberCount; i++) {
            long userId = i + 1;
            ConcertRequest.ReserveConcert param= new ConcertRequest.ReserveConcert(userId, saved.getScheduleId(), saved.getId());
            executorsService.submit(() -> {
                try {
                    ConcertResponse.ReservedSeatInfo reservedSeatInfo = concertFacade.reservedSeat(param);
                    System.out.println("Success: User " + userId + " reserved the seat.");
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorsService.shutdown();

        // then
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(39);

    }

    @DisplayName("같은 아이디가 5번 요청 하는경우, 한번만 결제 성공한다.")
    @Test
    void payReservedSeat() throws InterruptedException{
        // given
        int memberCount = 5;
        LocalDateTime now = LocalDateTime.now();

        Reservation reservation = Reservation.builder()
                .scheduleId(11L)
                .seatId(1L)
                .userId(17L)
                .seatNumber(5L)
                .price(5000L)
                .status(ReservationStatus.RESERVING)
                .createdAt(now)
                .build();
        reservationJpaRepository.save(reservation);
        ConcertRequest.PayReserveConcert request = new ConcertRequest.PayReserveConcert(17L, 11L, 1L, reservation.getId());

        UserPoint userPoint = UserPoint.builder()
                .userId(17L)
                .totalPoint(20000L)
                .build();
        userPointRepository.save(userPoint);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch latch = new CountDownLatch(memberCount);

        // when
        for (int i = 0; i < memberCount; i++) {
            executorsService.submit(() -> {
                try {
                    concertFacade.payReservedSeat(request);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorsService.shutdown();

        Reservation reservation1 = reservationJpaRepository.findById(reservation.getId()).orElseThrow();
        UserPoint userPoint1 = userPointRepository.findByUserId(17L)
                .orElseThrow();
        assertThat(reservation1.getStatus()).isEqualTo(ReservationStatus.RESERVED);
        assertThat(userPoint1.getTotalPoint()).isEqualTo(15000L);

        // then
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(4);
    }
}

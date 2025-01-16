package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ConcertSeatJpaRepository;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}

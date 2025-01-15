package kr.hhplus.be.server.api.concert.presentation.scheduler;

import kr.hhplus.be.server.api.concert.domain.service.ConcertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertScheduler {

    private final ConcertService concertService;

    @Scheduled(fixedDelayString = "60000")
    public void setConcertSeatStatus(){
        log.info("좌석 상태 변경 스케줄러");
        LocalDateTime now = LocalDateTime.now();
        concertService.findSeatInfo()
                .forEach(concertSeatModel->{
                    try {
                        if (concertSeatModel.checkCreatedAt(now))
                            concertService.changeSeatStatus(concertSeatModel.getSeatId());
                    } catch (Exception e) {
                        log.error("exception Seat ID: {}", concertSeatModel.getSeatId(), e);
                    }
                });
    }
}

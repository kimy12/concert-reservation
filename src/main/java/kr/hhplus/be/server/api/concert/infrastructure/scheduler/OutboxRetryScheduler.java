package kr.hhplus.be.server.api.concert.infrastructure.scheduler;

import kr.hhplus.be.server.api.concert.domain.enums.OutboxStatus;
import kr.hhplus.be.server.api.concert.domain.service.ConcertOutboxService;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertOutbox;
import kr.hhplus.be.server.api.concert.infrastructure.kafka.KafkaReservationEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRetryScheduler {

    private final ConcertOutboxService concertOutboxService;
    private final KafkaReservationEventProducer kafkaReservationEventProducer;

    @Scheduled(fixedDelay = 60000)
    public void retryFailedOutboxMessages() {
        log.info("Outbox 재처리 스케줄러 실행...");
        List<ConcertOutbox> failedOutboxMessages = concertOutboxService.findAllByFailed();
        for (ConcertOutbox outbox : failedOutboxMessages) {
            try {
                kafkaReservationEventProducer.sendMessage(outbox.getPayload());
                concertOutboxService.updateStatus(outbox.getKey(), OutboxStatus.PUBLISHED);
                log.info("Outbox 메시지 재처리 성공: reservationId={}", outbox.getKey());
            } catch (Exception e) {
                log.error("Outbox 메시지 재처리 실패: reservationId={}", outbox.getKey(), e);
            }
        }
    }
}

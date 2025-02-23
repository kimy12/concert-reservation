package kr.hhplus.be.server.api.concert.presentation.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.concert.application.event.ReservationEvent;
import kr.hhplus.be.server.api.concert.domain.enums.OutboxStatus;
import kr.hhplus.be.server.api.concert.domain.service.ConcertOutboxService;
import kr.hhplus.be.server.api.concert.infrastructure.ExternalDataTransferClient;
import kr.hhplus.be.server.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaReservationConsumer {
    private final ObjectMapper objectMapper;
    private final ConcertOutboxService concertOutboxService;
    private final ExternalDataTransferClient externalDataTransferClient;

    @KafkaListener(topics = KafkaConfig.TOPIC_RESERVATION, groupId = "reservation_group")
    public void consumeReservationEvent(String message) {
        ReservationEvent event = null;
        try {
            event = objectMapper.readValue(message, ReservationEvent.class);
            externalDataTransferClient.sendReservationData(event.scheduleId(), event.userId());
            concertOutboxService.updateStatus(event.reservationId(), OutboxStatus.PUBLISHED);
        } catch (Exception e) {
            log.error("Kafka Consumer에서 이벤트 처리 실패", e);
            if (event != null) {
                concertOutboxService.updateStatus(event.reservationId(), OutboxStatus.FAILED);
            }
        }
    }
}

package kr.hhplus.be.server.api.concert.presentation.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.concert.application.event.ReservationEvent;
import kr.hhplus.be.server.api.concert.domain.enums.OutboxStatus;
import kr.hhplus.be.server.api.concert.domain.service.ConcertOutboxService;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertOutbox;
import kr.hhplus.be.server.api.concert.infrastructure.kafka.KafkaReservationEventProducer;
import kr.hhplus.be.server.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final ConcertOutboxService concertOutboxService;
    private final KafkaReservationEventProducer kafkaReservationEventProducer;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleOutbox(ReservationEvent event) {
        try{
            String payload = objectMapper.writeValueAsString(event);
            concertOutboxService.save(new ConcertOutbox(KafkaConfig.TOPIC_RESERVATION, event.reservationId(), payload, event.createdAt() ,OutboxStatus.CREATED));
        }catch (JsonProcessingException e){
            log.error("handleOutbox error", e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleForMessaging(ReservationEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaReservationEventProducer.sendMessage(message);
        } catch (JsonProcessingException e) {
            log.error("handleMessaging error", e);
        }
    }

}

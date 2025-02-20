package kr.hhplus.be.server.api.concert.infrastructure.kafka;

import kr.hhplus.be.server.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaReservationEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        try {
            kafkaTemplate.send(KafkaConfig.TOPIC_RESERVATION, message);
            log.info("Kafka 메시지 전송 성공: {}", message);
        } catch (Exception e) {
            log.error("Kafka 메시지 전송 실패: {}", message, e);
        }
    }
}

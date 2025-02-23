package kr.hhplus.be.server.api.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaTestProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC_NAME = "concert-topic";

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }
}

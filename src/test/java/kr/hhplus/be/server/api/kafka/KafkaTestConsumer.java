package kr.hhplus.be.server.api.kafka;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Getter
@Component
@Slf4j
public class KafkaTestConsumer {

    private static final String TOPIC_NAME = "concert-topic";

    private String receivedMessage;

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = TOPIC_NAME, groupId = "test-group")
    public void consume(String message) {
        log.info("Consumed message: {}", message);
        this.receivedMessage = message;
        latch.countDown();
    }

    public void resetLatch() {
        this.latch = new CountDownLatch(1);
    }

}

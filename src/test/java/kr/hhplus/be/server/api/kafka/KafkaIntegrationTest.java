package kr.hhplus.be.server.api.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"concert-topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaIntegrationTest {

    @Autowired
    private KafkaTestProducer kafkaProducer;

    @Autowired
    private KafkaTestConsumer kafkaConsumer;

    @BeforeEach
    void setUp() {
        kafkaConsumer.resetLatch();
    }

    @DisplayName("카프카 연결 테스트")
    @Test
    void testKafkaMessaging() throws InterruptedException {
        // given
        String testMessage = "Hello, Kafka!";

        // when
        kafkaProducer.sendMessage(testMessage);

        // then
        boolean messageConsumed = kafkaConsumer.getLatch().await(5, TimeUnit.SECONDS);
        assertThat(messageConsumed).isTrue();
        assertThat(kafkaConsumer.getReceivedMessage()).isEqualTo(testMessage);
    }
}

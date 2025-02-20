package kr.hhplus.be.server.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_RESERVATION = "reservation.created";
}

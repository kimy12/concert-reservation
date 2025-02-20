package kr.hhplus.be.server.api.concert.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.api.concert.domain.enums.OutboxStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class ConcertOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    private Long key;

    private String payload;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    public ConcertOutbox(String topic, Long key, String payload, LocalDateTime createdAt, OutboxStatus status) {
        this.topic = topic;
        this.key = key;
        this.payload = payload;
        this.createdAt = createdAt;
        this.status = status;
    }
}

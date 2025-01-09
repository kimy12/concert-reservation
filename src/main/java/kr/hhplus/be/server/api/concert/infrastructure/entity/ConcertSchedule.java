package kr.hhplus.be.server.api.concert.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertId; // fk

    private Long seq;

    LocalDateTime startAt;

//    public void toDomain (Long id, Long concertId, Long seq, LocalDateTime startAt) {
//        this.id = id;
//        this.concertId = concertId;
//        this.seq = seq;
//        this.startAt = startAt;
//    }

    @Builder
    public ConcertSchedule(Long id, Long concertId, Long seq, LocalDateTime startAt) {
        this.id = id;
        this.concertId = concertId;
        this.seq = seq;
        this.startAt = startAt;
    }
}

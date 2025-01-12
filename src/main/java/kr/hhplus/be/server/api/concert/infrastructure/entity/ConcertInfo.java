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
public class ConcertInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    LocalDateTime bookingStartAt;

    LocalDateTime bookingEndAt;

    @Builder
    public ConcertInfo(Long id, String title, LocalDateTime bookingStartAt, LocalDateTime bookingEndAt) {
        this.id = id;
        this.title = title;
        this.bookingStartAt = bookingStartAt;
        this.bookingEndAt = bookingEndAt;
    }
}

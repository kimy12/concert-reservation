package kr.hhplus.be.server.api.concert.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long seatId;

    private Long scheduleId;

    private Long seatNumber;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long price;

    @Builder
    public Reservation(Long id, Long userId, Long seatNumber, Long seatId, Long scheduleId, ReservationStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, Long price) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.userId = userId;
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
    }

    public ReservationModel toDto() {
        return ReservationModel.builder()
                .id(this.id)
                .seatNumber(seatNumber)
                .userId(this.userId)
                .seatId(this.seatId)
                .scheduleId(this.scheduleId)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .price(this.price)
                .build();
    }
}

package kr.hhplus.be.server.api.concert.domain.dto;

import kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDto {

    private Long id;

    private Long userId;

    private Long seatId;

    private Long seatNumber;

    private Long scheduleId;

    private ReservationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long price;

    @Builder
    public ReservationDto(Long id, Long seatNumber, Long userId, Long seatId, Long scheduleId, ReservationStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, Long price) {
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

    public Reservation toEntity() {
        return Reservation.builder()
                .id(this.id)
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

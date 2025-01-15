package kr.hhplus.be.server.api.concert.domain.model;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.SEAT_NOT_AVAILABLE;
import static kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus.RESERVING;

@Getter
@Setter
public class ReservationModel {

    private Long id;

    private Long userId;

    private Long seatId;

    private Long seatNumber;

    private Long scheduleId;

    private ReservationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long price;

    // 처음 자리 선정을 위한 생성자
    public ReservationModel(Long userId, Long seatId, Long seatNumber, Long scheduleId, Long price) {
        this.userId = userId;
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.scheduleId = scheduleId;
        this.status = RESERVING;
        this.createdAt = LocalDateTime.now();
        this.price = price;
    }

    @Builder
    public ReservationModel(Long id, Long seatNumber, Long userId, Long seatId, Long scheduleId, ReservationStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, Long price) {
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

    public void checkCreatedAt (LocalDateTime now) {
        LocalDateTime expiredAt = this.createdAt.plusMinutes(5);
        if(expiredAt.isBefore(now)){
            throw new CustomException(SEAT_NOT_AVAILABLE);
        }
    }
}

package kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r " +
            "from Reservation r " +
            "where r.status != 'CANCELED' " +
            "and r.seatId = :seatId")
    Optional<Reservation> findBySeatId(@Param("seatId") Long seatId);

    Optional<Reservation> findByUserId(Long userId);

    @Modifying
    @Query("UPDATE Reservation r SET r.status = :status, r.updatedAt = :updatedAt WHERE r.id = :reservationId")
    Optional<Reservation> updateStatusById(@Param("reservationId") Long reservationId,
                          @Param("status") ReservationStatus status,
                          @Param("updatedAt") LocalDateTime updatedAt);
}

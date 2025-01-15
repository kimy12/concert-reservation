package kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

    Optional<ConcertSeat> findBySeatNumberAndScheduleId(Long seatNumber, Long scheduleId);
}

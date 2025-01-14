package kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository;

import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

    Optional<ConcertSeat> findBySeatNumberAndScheduleId(Long seatNumber, Long scheduleId);
}

package kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository;

import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertSchedule, Long> {
}

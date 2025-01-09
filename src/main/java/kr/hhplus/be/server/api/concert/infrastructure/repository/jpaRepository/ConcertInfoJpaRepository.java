package kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository;

import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertInfoJpaRepository extends JpaRepository<ConcertInfo, Long> {
}

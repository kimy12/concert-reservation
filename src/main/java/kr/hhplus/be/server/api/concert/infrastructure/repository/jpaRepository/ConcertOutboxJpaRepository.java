package kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository;

import kr.hhplus.be.server.api.concert.domain.enums.OutboxStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConcertOutboxJpaRepository extends JpaRepository<ConcertOutbox, Long> {

    List<ConcertOutbox> findByStatus(OutboxStatus status);

    void updateStatusByKey(Long key, OutboxStatus status);
}

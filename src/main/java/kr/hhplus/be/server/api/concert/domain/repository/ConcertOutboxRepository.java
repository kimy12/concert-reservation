package kr.hhplus.be.server.api.concert.domain.repository;

import kr.hhplus.be.server.api.concert.domain.enums.OutboxStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertOutbox;

import java.util.List;

public interface ConcertOutboxRepository {

    void save(ConcertOutbox concertOutbox);

    void updateStatus(Long key, OutboxStatus outboxStatus);

    List<ConcertOutbox> findByStatus(OutboxStatus status);
}

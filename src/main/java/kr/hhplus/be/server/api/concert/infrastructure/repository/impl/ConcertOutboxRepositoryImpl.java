package kr.hhplus.be.server.api.concert.infrastructure.repository.impl;

import kr.hhplus.be.server.api.concert.domain.enums.OutboxStatus;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertOutboxRepository;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertOutbox;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ConcertOutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertOutboxRepositoryImpl implements ConcertOutboxRepository {

    private final ConcertOutboxJpaRepository concertOutboxJpaRepository;

    @Override
    public void save(ConcertOutbox concertOutbox) {
        concertOutboxJpaRepository.save(concertOutbox);
    }

    @Override
    public void updateStatus(Long key, OutboxStatus outboxStatus) {
        concertOutboxJpaRepository.updateStatusByKey(key, outboxStatus);
    }

    @Override
    public List<ConcertOutbox> findByStatus(OutboxStatus status) {
        return concertOutboxJpaRepository.findByStatus(status);
    }
}

package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.concert.domain.enums.OutboxStatus;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertOutboxRepository;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertOutboxService {

    private final ConcertOutboxRepository concertOutboxRepository;

    @Transactional
    public void save (ConcertOutbox concertOutbox) {
        concertOutboxRepository.save(concertOutbox);
    }

    @Transactional
    public void updateStatus (Long outboxId, OutboxStatus outboxStatus) {
        concertOutboxRepository.updateStatus(outboxId, outboxStatus);
    }

    public List<ConcertOutbox> findAllByFailed () {
        return concertOutboxRepository.findByStatus(OutboxStatus.FAILED);
    }
}

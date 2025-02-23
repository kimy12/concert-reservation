package kr.hhplus.be.server.api.concert.application.event.listener;

import kr.hhplus.be.server.api.concert.application.event.AfterReservationConfirmedEvent;
import kr.hhplus.be.server.api.concert.infrastructure.ExternalDataTransferClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class AfterReservationEventListener {
    private final ExternalDataTransferClient externalDataTransferClient;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationConfirmed(AfterReservationConfirmedEvent event) {
        log.info("Reservation Succeeded {} ", event);
        try{
            externalDataTransferClient.sendReservationData(event.scheduleId(), event.userId());
        } catch (Exception e) {
            log.error(" error {} ", e.getMessage());
        }
    }

}

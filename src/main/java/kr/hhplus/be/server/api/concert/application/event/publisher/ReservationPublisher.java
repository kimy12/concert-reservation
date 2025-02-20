package kr.hhplus.be.server.api.concert.application.event.publisher;

import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(ReservationModel reservation) {
        applicationEventPublisher.publishEvent(reservation);
    }
}

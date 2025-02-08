package kr.hhplus.be.server.api.token.presentation.scheduler;

import kr.hhplus.be.server.api.token.domain.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenScheduler {

    private final TokenService tokenService;

    @Scheduled(fixedDelayString = "60000")
    public void toTokenStatusActive(){
        log.info("Pending token to Active Token");
        LocalDateTime now = LocalDateTime.now();
        tokenService.findAllPendingTokens()
                .forEach(token -> {
                    try {
                        tokenService.changeTokenStatusActive(token, now);
                    } catch (Exception e) {
                        log.error("exception Token ID: {}", token, e);
                    }
                });

    }
}

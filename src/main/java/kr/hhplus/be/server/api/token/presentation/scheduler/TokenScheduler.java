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

    private static final int ACTIVE_TOKEN = 50;
    private final TokenService tokenService;

    @Scheduled(fixedDelayString = "60000")
    public void toTokenStatusActive(){
        log.info("토큰 활성화 스케줄러");
        tokenService.findAllPendingTokens(ACTIVE_TOKEN)
                .forEach(token -> {
                    try {
                        tokenService.changeTokenStatusActive(token.getId());
                    } catch (Exception e) {
                        log.error("exception Token ID: {}", token.getId(), e);
                    }
                });

    }

    @Scheduled(fixedDelayString = "60000")
    public void toDeleteToken(){
        log.info("토큰 삭제 스케줄러");
        LocalDateTime now = LocalDateTime.now();
        tokenService.findAllByTokenStatusActive()
                .forEach(token -> {
                    try {
                        if (token.checkExpireAt(now)) tokenService.deleteTokenInfo(token.getId());
                    } catch (Exception e) {
                        log.error("exception Token ID: {}", token.getId(), e);
                    }
                });
    }
}

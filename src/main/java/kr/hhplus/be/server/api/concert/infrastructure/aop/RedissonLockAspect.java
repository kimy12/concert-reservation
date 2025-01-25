package kr.hhplus.be.server.api.concert.infrastructure.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonLockAspect {
    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final RedissonCallTransaction redissonCallTransaction;

    @Around("@annotation(RedissonLock) && (args(..)) ")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);

        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(methodSignature.getParameterNames(), joinPoint.getArgs(), redissonLock.key());
        RLock lock = redissonClient.getLock(key);

        try {
            boolean isPossible = lock.tryLock(redissonLock.waitTime(), redissonLock.leaseTime(), redissonLock.timeUnit());
            if (!isPossible) {
                log.warn("키 생성 실패: {}", key);
                throw new IllegalStateException("Could not acquire lock for key: " + key);
            }
            log.info("key : {}", key);
            return redissonCallTransaction.proceed(joinPoint);
        } catch (Exception e) {
            throw new InterruptedException(e.getMessage());
        }finally {
            try {
                lock.unlock();
                log.info("락해제 : {}", key);
            } catch (IllegalMonitorStateException e) {
                log.warn("Redisson 락이 이미 해제되었습니다 lockName: " + key);
            }
        }
    }
}

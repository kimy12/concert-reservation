package kr.hhplus.be.server.api.concert.infrastructure.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RedissonCallTransaction {

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 2)
    public Object proceed(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed();
    }
}

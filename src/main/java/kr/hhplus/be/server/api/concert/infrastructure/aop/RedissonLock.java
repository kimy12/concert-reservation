package kr.hhplus.be.server.api.concert.infrastructure.aop;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {

    String key();

    long waitTime() default 5L;

    long leaseTime() default 3L;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

}

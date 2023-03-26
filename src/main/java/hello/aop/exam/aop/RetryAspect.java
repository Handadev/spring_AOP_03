package hello.aop.exam.aop;

import hello.aop.exam.anntation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry = {}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exHolder = null;


        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] retry count = {} / {}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exHolder = e;
            }
        }
        throw exHolder;
    }
}

package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // 현재는 private로 설정을 했지만, public으로 설정하면 내 외부 어드바이스에서 사용할 수 있게 된다
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){}; // 포인트컷 시그니처 단독 구성

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("log = {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}

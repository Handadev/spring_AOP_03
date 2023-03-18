package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV4Pointcut {


    @Around("hello.aop.order.aop.PointCuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("log = {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    // hello.aop.order 하위 패키지 이면서 플래스 일므 패턴이 *Service 인것
    @Around("hello.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("트랜젝션 시작 {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("트랜젝션 종료 {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("트랜젝션 롤백 {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("리소스 릴리즈 {}", joinPoint.getSignature());
        }
    }
}

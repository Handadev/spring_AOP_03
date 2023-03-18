package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
// 순서는 클래스 단위로밖에 할 수 없다
public class AspectV6Advice {

    // Around를 제외한 다른 어노테이션들은 JoinPoint를 선언하지 않아도 된다
    // ProceedingJoinPoint에는 proceed가 선언되어있다
    // Around를 제외한 나머지는 작업의 흐름을 방해할 수 없다
    @Around("hello.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // Before
            log.info("트랜젝션 시작 {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            // AfterReturning
            log.info("트랜젝션 종료 {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // AfterThrowing
            log.info("트랜젝션 롤백 {}", joinPoint.getSignature());
            throw e;
        } finally {
            // After
            log.info("리소스 릴리즈 {}", joinPoint.getSignature());
        }
    }

    // join point 안 넣어도 실행할 수 있다
    @Before("hello.aop.order.aop.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("Before {}", joinPoint.getSignature());
    }

    // returning 과 매개변수의 이름은 같아야한다
    @AfterReturning(value = "hello.aop.order.aop.PointCuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        // Around에서는 return을 조작할 수 있지만 AfterReturn에서는 안된다
        log.info("Return {}", joinPoint.getSignature(), result);
    }

    // throwing 과 매개변수의 이름은 같아야한다
    @AfterThrowing(value = "hello.aop.order.aop.PointCuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        // around에서는 throw를 설정해줬지만, 여기서는 자동으로 된다
        log.info("ex {}", joinPoint.getSignature(), ex);
    }

    @After(value = "hello.aop.order.aop.PointCuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("After {}", joinPoint.getSignature());
    }

}

package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    // hello.aop.order 패키지 하위 모든 것
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){}

    // 클래스 이름 패턴이 *Service 인것
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    // allOrder + allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}

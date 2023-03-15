package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopInfoTest(){
        log.info("aopProxy , orderService = {}", AopUtils.isAopProxy(orderService));
        log.info("aopProxy , orderRepository = {}", AopUtils.isAopProxy(orderRepository));
        
    }
    
    @Test
    void successTest(){
        orderService.orderItem("a");
        // given
        // when
        // then
    }

    @Test
    void exceptionTest(){
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);

        // given
        // when
        // then
    }
}

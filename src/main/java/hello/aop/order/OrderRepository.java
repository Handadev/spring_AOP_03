package hello.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class OrderRepository {

    public String save(String itemId) {
        log.info("OrderRepository 실행");
        if (itemId.equals("ex")) throw new IllegalStateException("얘외발생");

        return "ok";
    }
}

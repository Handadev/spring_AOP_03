package hello.aop.exam;

import hello.aop.exam.anntation.Retry;
import hello.aop.exam.anntation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    @Trace
    @Retry(4)
    public String save(String itemId) {
        seq ++;
        if (seq % 5 == 0) {
            throw new IllegalArgumentException("5번 오류발생");
        }
        return "ok";
    }
}

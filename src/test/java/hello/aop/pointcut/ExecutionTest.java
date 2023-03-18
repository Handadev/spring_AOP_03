package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }


    @Test
    void printTest(){
        // public java.lang.String hello.aop.order.member.MemberServiceImpl.hello(java.lang.String)
        log.info("Hello Method = {}", helloMethod);

    }


    /**
     * 기본 작성
     */
    @Test // 제일 정확하게 작성
    void exactMatchTest(){
        // |public| java.lang.|String| |hello.aop.order.member.MemberServiceImpl.hello|(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test // 가장 많이 생략
    void allMatchTest(){
        // * 반환타입 전부 * 메소드 전부 (..) 파라미터 타입 / 개수 모두 상관 없음
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    /**
     * 매칭
     */
    @Test
    void nameMatchTest(){
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void namePatternMatchTest(){
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void namePatternMatchTest2(){
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void namePatternMatchFalseTest(){
        pointcut.setExpression("execution(* name(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


    /**
     * 패키지 매칭
     */
    @Test
    void packageExactTest(){
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void packageExactTest2(){
        // member 하위 클래스 전부 매소드 전부
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void packageFalseTest(){
        // aop 하위 패키지 전부로 찾기 때문에 경로가 달라서 false
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    @Test
    void subPackageFalseTest(){
        // .. 으로 aop와 그 하위 패키지를 전부 성공으로 처리
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    /**
     * 타입 매칭
     */
    @Test
    void typeExactMatchTest(){
        pointcut.setExpression("execution(*  hello.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void typeSuperMatchTest(){
        // 부모 타입으로 선언해도 자식 타입 매칭된다
        pointcut.setExpression("execution(*  hello.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void typeSuperMatchFalseTest() throws NoSuchMethodException {
        // 부모 타입에만 있는 걸로만 할 수 있다
        pointcut.setExpression("execution(*  hello.aop.member.MemberService.*(..))");
        Method internal = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internal, MemberServiceImpl.class)).isFalse();
    }


    /**
     * 파라미터 매칭
     */
    @Test
    void argsMatchTest(){
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void noArgsMatchTest(){
        // 파라미터 없는 것은 이렇게 선언
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    @Test
    void exactParamMatchTest(){
        // 단 하나의 파라미터만 허용하지만, 타입은 자유
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void exactParamMatchTest2(){
        // 갯수도 타입도 모두 허용
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void exactParamMatchTest3(){
        // (String), (String, xx), (String, xx, xx) 의 경우
        // (String) 타입으로 시작하며, 숫자, 모든 타입 허용
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void exactParamMatchTest4(){
        // (String), (String, xx)의 경우
        // 숫자는 두 개로 확정인 경우
        pointcut.setExpression("execution(* *(String, * ))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}

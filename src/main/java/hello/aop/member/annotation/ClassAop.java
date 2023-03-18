package hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 타입 설정하는 어노테이션
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAop {
}

package cc.yiueil.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LogAnnotation 方法调用通知
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:06
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LogAnnotation {
    /**
     * @return 前置日志
     */
    String before() default "默认日志前置内容";

    /**
     * @return 后置日志
     */
    String after() default "默认日志后置内容";

    /**
     * @return 异常日志
     */
    String exception() default "默认日志异常内容";
}

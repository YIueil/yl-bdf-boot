package cc.yiueil.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TimeInterval 接口调用计时器
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:07
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TimeInterval {
    /**
     * @return  满足条件时要输出的内容
     */
    String value() default "方法调用耗时过长";

    /**
     * @return  过滤小于多少时间的方法(默认5秒)
     */
    long less() default 5000;

    /**
     * @return 是否打印方法信息
     */
    boolean printMethodMessage() default true;
}

package cc.yiueil.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VerifyToken 开放式接口token校验
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/3/21 10:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface VerifyToken {
}

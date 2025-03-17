package cc.yiueil.advise;

import cc.yiueil.annotation.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * LogAdvise 方法调用日志环绕通知
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:06
 * @version 1.0
 */
@Aspect
@Component
@Slf4j
public class LogAdvise {
    @Around(value = "@annotation(cc.yiueil.annotation.LogAnnotation)")
    public Object logAdvice(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        Object proceed = null;
        Class<?> targetClass = proceedingJoinPoint.getTarget().getClass();
        Signature signature = proceedingJoinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature ms = (MethodSignature) signature;
            Method declaredMethod = targetClass.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
            if (declaredMethod.isAnnotationPresent(LogAnnotation.class)) {
                LogAnnotation declaredAnnotation = declaredMethod.getDeclaredAnnotation(LogAnnotation.class);
                String before = declaredAnnotation.before();
                String after = declaredAnnotation.after();
                String exception = declaredAnnotation.exception();
                log.debug(before);
                try {
                    proceed = proceedingJoinPoint.proceed();
                } catch (Throwable throwable) {
                    log.debug(exception);
                    log.error(throwable.getMessage(), throwable);
                }
                log.debug(after);
            }
        }
        return proceed;
    }
}

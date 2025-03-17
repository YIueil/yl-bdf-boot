package cc.yiueil.advise;

import cc.yiueil.annotation.TimeInterval;
import cc.yiueil.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * TimeIntervalAdvise 方法耗时环绕通知
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:06
 * @version 1.0
 */
@Aspect
@Component
@Slf4j
public class TimeIntervalAdvise {
    @Around(value = "@annotation(cc.yiueil.annotation.TimeInterval)")
    public Object logAdvice(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        Object proceed = null;
        Class<?> targetClass = proceedingJoinPoint.getTarget().getClass();
        Signature signature = proceedingJoinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature ms = (MethodSignature) signature;
            Method declaredMethod = targetClass.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
            if (declaredMethod.isAnnotationPresent(TimeInterval.class)) {
                TimeInterval timeInterval = declaredMethod.getDeclaredAnnotation(TimeInterval.class);
                long less = timeInterval.less();
                StopWatch sw = new StopWatch();
                sw.start();
                try {
                    proceed = proceedingJoinPoint.proceed();
                    sw.stop();
                } catch (Throwable throwable) {
                    log.error(throwable.getMessage(), throwable);
                    sw.stop();
                }
                if (sw.getTotalTimeMillis() > less) {
                    print(declaredMethod, timeInterval, sw);
                }
            }
        }
        return proceed;
    }

    private void print(Method method, TimeInterval timeInterval, StopWatch sw) {
        StringBuilder sb = new StringBuilder();
        if (timeInterval.printMethodMessage()) {
            sb.append(method);
            sb.append(" ");
        }
        if (StringUtils.isNotBlank(timeInterval.value())) {
            sb.append(timeInterval.value());
            sb.append(" ");
        }
        sb.append(sw.getTotalTimeMillis());
        log.warn(sb.toString());
    }
}

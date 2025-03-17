package cc.yiueil.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * SpringContextUtils spring容器工具类
 * @author 弋孓 YIueil@163.com
 * @date 2022/7/26 0:10
 * @version 1.0
 */
@Slf4j
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (applicationContext.containsBean(beanId)) {
            return (T) applicationContext.getBean(beanId);
        } else {
            log.error(String.format("not find bean : %s", beanId));
            return null;
        }
    }

    public static <T> T getBean(Class<T> cls) {
        return applicationContext.getBean(cls);
    }

    /**
     * 获取到类路径资源
     * @param locationPattern 类路径资源路径 eg: classpath:*.xml
     * @return 类路径下匹配到的资源
     * @throws IOException IO相关异常
     */
    public static Resource[] getResources(String locationPattern) throws IOException {
        return applicationContext.getResources(locationPattern);
    }
}

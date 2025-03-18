package cc.yiueil.util.ext;

import cc.yiueil.util.ArrayUtils;
import cc.yiueil.util.ObjectUtils;
import ch.qos.logback.classic.spi.IThrowableProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * GlobalProperties 全局配置属性类
 * 修订记录:
 * v1.0: 实现从classpath中加载所有的配置文件
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/31 23:22
 */
@Slf4j
public class GlobalProperties {
    /**
     * 数据已加载标识
     */
    public static Boolean load = false;

    /**
     * 全局配置properties
     */
    public static Properties globalProperties = new Properties();

    /**
     * 默认路径加载配置文件: classpath:properties/*.properties
     */
    public static void load() {
        load(null);
    }

    /**
     * 指定配置文件路径加载配置文件
     *
     * @param locationPattern 指定配置文件路径
     */
    public static void load(String locationPattern) {
        try {
            Resource[] resources = SpringContextUtils.getResources(ObjectUtils.defaultIfNull(locationPattern, "classpath:properties/*.properties"));
            if (ArrayUtils.isNotEmpty(resources)) {
                for (Resource resource : resources) {
                    globalProperties.load(resource.getInputStream());
                }
                log.debug(String.format("globalProperties: %s", globalProperties));
                load = true;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 重新加载全局配置
     */
    public static void reload() {
        reload(null);
    }

    /**
     * 使用指定配置文件路径重新加载全局配置
     *
     * @param locationPattern 指定配置文件路径
     */
    public static void reload(String locationPattern) {
        clear();
        load(locationPattern);
    }

    private static void clear() {
        globalProperties.clear();
        load = false;
    }

    /**
     * 全局获取配置项, 不存在使用默认值
     *
     * @param key          键值
     * @param defaultValue 默认值
     * @return value
     */
    public static String getProperties(String key, String defaultValue) {
        if (!load) {
            load(null);
        }
        return ObjectUtils.defaultIfNull(globalProperties.getProperty(key), defaultValue);
    }

    /**
     * 全局获取配置项, 不存在时报错
     *
     * @param key          键值
     * @return value
     */
    public static <X extends Throwable> String getPropertiesOrElseThrow(String key, Supplier<? extends X> exceptionSupplier) throws X {
        if (!load) {
            load(null);
        }
        return Optional.ofNullable(globalProperties.getProperty(key)).orElseThrow(exceptionSupplier);
    }
}

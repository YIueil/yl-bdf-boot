package cc.yiueil.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converter 转换器接口
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:49
 * @version 1.0
 * <pre>
 *     转换器添加步骤：
 *     1、添加接口实现
 *     2、在ConverterHolder中添加对应的转换器实例
 * </pre>
 */
public interface Converter<T> {
    Logger log = LoggerFactory.getLogger(Converter.class);

    /**
     * 转换 默认不忽略转换错误
     *
     * @param obj          需转换的对象
     * @param defaultValue 默认值
     * @return 转换结果
     */
    T convert(Object obj, T defaultValue);

    /**
     * 转换时是否需要忽略本次转换错误
     *
     * @param obj          需转换的对象
     * @param defaultValue 默认值
     * @param isCheck      是否忽略转换错误
     * @return 转换结果
     */
    default T convert(Object obj, T defaultValue, boolean isCheck) {
        if (isCheck) {
            return convert(obj, defaultValue);
        } else {
            try {
                return convert(obj, defaultValue);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return defaultValue;
    }
}

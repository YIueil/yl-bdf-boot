package cc.yiueil.convert.impl.base;

import cc.yiueil.convert.Converter;

/**
 * LongConverter
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:48
 * @version 1.0
 */
public class LongConverter implements Converter<Long> {
    @Override
    public Long convert(Object obj, Long defaultValue) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        if (obj instanceof CharSequence) {
            return Long.parseLong(obj.toString());
        }
        return defaultValue;
    }
}

package cc.yiueil.convert.impl.base;

import cc.yiueil.convert.Converter;

/**
 * BooleanConverter
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:47
 * @version 1.0
 */
public class BooleanConverter implements Converter<Boolean> {
    @Override
    public Boolean convert(Object obj, Boolean defaultValue) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue() > 0;
        }
        if (obj instanceof CharSequence) {
            return Boolean.parseBoolean(obj.toString());
        }
        return defaultValue;
    }
}

package cc.yiueil.convert.impl.base;

import cc.yiueil.convert.Converter;

/**
 * FloatConverter
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:48
 * @version 1.0
 */
public class FloatConverter implements Converter<Float> {
    @Override
    public Float convert(Object obj, Float defaultValue) {
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        if (obj instanceof CharSequence) {
            return Float.parseFloat(obj.toString());
        }
        return defaultValue;
    }
}

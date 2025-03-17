package cc.yiueil.convert.impl.base;

import cc.yiueil.convert.Converter;

/**
 * DoubleConverter
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:48
 * @version 1.0
 */
public class DoubleConverter implements Converter<Double> {
    @Override
    public Double convert(Object obj, Double defaultValue) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof CharSequence) {
            return Double.parseDouble(obj.toString());
        }
        return defaultValue;
    }
}

package cc.yiueil.convert.impl.base;

import cc.yiueil.convert.Converter;

/**
 * IntegerConverter
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:48
 * @version 1.0
 */
public class IntegerConverter implements Converter<Integer> {
    @Override
    public Integer convert(Object obj, Integer defaultValue) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        if (obj instanceof CharSequence) {
            return Integer.parseInt(obj.toString());
        }
        return defaultValue;
    }
}

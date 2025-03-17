package cc.yiueil.convert.impl.base;

import cc.yiueil.convert.Converter;

/**
 * StringConverter
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:48
 * @version 1.0
 */
public class StringConverter implements Converter<String> {
    @Override
    public String convert(Object obj, String defaultValue) {
        return obj == null ? defaultValue : obj.toString();
    }
}

package cc.yiueil.convert;

import cc.yiueil.convert.impl.base.*;
import cc.yiueil.lang.reflect.AbstractTypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * ConverterHolder 转换器的持有对象
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:50
 * @version 1.0
 */
public class ConverterHolder {
    private Map<Type, Converter<?>> defaultConverterMap;
    private Map<Type, Converter<?>> customConverterMap;

    private static class SingletonRegistry { // todo 这部分写法列为自己的代码规范
        private static final ConverterHolder INSTANCE = new ConverterHolder();
    }

    public static ConverterHolder getInstance() {
        return SingletonRegistry.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    private static <T> Converter<T> getDefaultConverter(Type type) {
        return (getInstance().defaultConverterMap == null || getInstance().defaultConverterMap.size() == 0) ? null : (Converter<T>)getInstance().defaultConverterMap.get(type);
    }

    @SuppressWarnings("unchecked")
    private static <T> Converter<T> getCustomConverter(Type type) {
        return (getInstance().customConverterMap == null || getInstance().customConverterMap.size() == 0) ? null : (Converter<T>)getInstance().customConverterMap.get(type);
    }

    public static <T> Converter<T> getConverter(AbstractTypeReference<T> abstractTypeReference) {
        Type type = abstractTypeReference.getType();
        // 当泛型T还包含泛型时,需要获取到不包括泛型的Type,否则找不到对应的转换器
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        }
        Converter<T> converter;
        converter = getCustomConverter(type);
        if (converter == null) {
            converter = getDefaultConverter(type);
        }
        if (converter == null) {
            throw new RuntimeException("G! 没有找到转换器:" + abstractTypeReference.getType().toString());
        }
        return converter;
    }

    private ConverterHolder() {
        loadDefaultConverter();
        loadCustomConverter();
    }

    private void loadCustomConverter() {
        defaultConverterMap = new HashMap<>(16);
        defaultConverterMap.put(String.class, new StringConverter());
        defaultConverterMap.put(Date.class, new DateConverter());
        defaultConverterMap.put(Integer.class, new IntegerConverter());
        defaultConverterMap.put(FloatConverter.class, new FloatConverter());
        defaultConverterMap.put(Double.class, new DoubleConverter());
        defaultConverterMap.put(Long.class, new LongConverter());
        defaultConverterMap.put(Boolean.class, new BooleanConverter());
    }

    private void loadDefaultConverter() {
        customConverterMap = new HashMap<>(16);
        // todo 添加对于自定义转换器的扫描添加支持
    }
}

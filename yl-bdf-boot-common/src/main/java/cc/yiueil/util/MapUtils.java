package cc.yiueil.util;

import cc.yiueil.convert.Converter;
import cc.yiueil.convert.ConverterHolder;
import cc.yiueil.lang.reflect.AbstractTypeReference;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MapUtils
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:20
 * @version 1.0
 */
@Slf4j
public class MapUtils {
    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    /**
     * Map是否不为空
     *
     * @param map 集合
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }


    /**
     * 按照值排序，可选是否倒序
     *
     * @param map    需要对值排序的map
     * @param <K>    键类型
     * @param <V>    值类型
     * @param isDesc 是否倒序
     * @return 排序后新的Map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = new LinkedHashMap<>();
        Comparator<Map.Entry<K, V>> entryComparator = Map.Entry.comparingByValue();
        if (isDesc) {
            entryComparator = entryComparator.reversed();
        }
        map.entrySet().stream().sorted(entryComparator).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    /**
     * Map转换为实体
     * @param map map对象
     * @param clazz 实例class类型
     * @param <T> class泛型
     * @return 转换后的实体
     * @throws Exception 转换异常
     */
    @SuppressWarnings("all")
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> clazz) throws Exception {
        T entity = clazz.newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            Object value = map.get(fieldName);
            if (value == null) {
                value = map.get(fieldName.toLowerCase());
            }
            if (value != null) {
                field.setAccessible(true);
                AbstractTypeReference abstractTypeReference = AbstractTypeReference.of(field.getType());
                Converter converter = ConverterHolder.getConverter(abstractTypeReference);
                if (ObjectUtils.isNull(converter)) {
                    continue;
                }
                field.set(entity, converter.convert(value, null));
            }
        }
        return entity;
    }

    /**
     * 实体转换为Map
     * @param entity 实体
     * @param <T> class泛型
     * @return 转换后的Map
     */
    public static <T> Map<String, Object> entityToMap(T entity) {
        Map<String, Object> map = new HashMap<>(16);
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
            map.put(field.getName(), value);
        }
        return map;
    }
}

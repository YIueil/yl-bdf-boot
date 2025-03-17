package cc.yiueil.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * BeanUtils
 * 主要针对简单类型转换进行返回值封装增强
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/10 0:50
 */
public class BeanUtils {
    private static final String[] NULL_IGNORE_PROPERTIES = {};
    public static <T> T copyProperties(Object source, T target) throws BeansException {
        org.springframework.beans.BeanUtils.copyProperties(source, target, NULL_IGNORE_PROPERTIES);
        return target;
    }

    public static <T> T copyProperties(Object source, T target, Boolean ignoreNullProperties) throws BeansException {
        if (ignoreNullProperties) {
            org.springframework.beans.BeanUtils.copyProperties(source, target, getNullField(source));
        } else {
            org.springframework.beans.BeanUtils.copyProperties(source, target, NULL_IGNORE_PROPERTIES);
        }
        return target;
    }

    public static <T> T copyProperties(Object source, T target, String... ignoreProperties) throws BeansException {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    /**
     * 获取属性中为空的字段
     *
     * @param source 属性对象
     * @return 空属性对象集合
     */
    private static String[] getNullField(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> notNullFieldSet = new HashSet<>();
        if (propertyDescriptors.length > 0) {
            for (PropertyDescriptor p : propertyDescriptors) {
                String name = p.getName();
                Object value = beanWrapper.getPropertyValue(name);
                if (Objects.isNull(value)) {
                    notNullFieldSet.add(name);
                }
            }
        }
        String[] notNullField = new String[notNullFieldSet.size()];
        return notNullFieldSet.toArray(notNullField);
    }
}

package cc.yiueil.util;

import java.util.Collection;

/**
 * CollectionUtils 集合工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:15
 * @version 1.0
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        return collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}

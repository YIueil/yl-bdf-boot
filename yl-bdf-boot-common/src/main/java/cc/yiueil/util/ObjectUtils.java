package cc.yiueil.util;

/**
 * ObjectUtils
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:32
 * @version 1.0
 */
public class ObjectUtils {
    /**
     * 获取对象, 如果传入的对象为空, 则返回使用默认值
     * @param t 传入的对象
     * @param defaultValue 传入对象为空时提供的默认值
     * @param <T> 参数类型
     * @return 传入对象为空时返回 t, 否则返回 defaultValue
     */
    public static <T> T defaultIfNull(T t, T defaultValue) {
        return t != null ? t : defaultValue;
    }

    /**
     * 判断传入的对象是否全部为null,
     * @param objects 对象集合
     * @return 是则返回true, 否则返回false
     */
    public static boolean isAllNull(Object... objects) {
        for (Object obj : objects) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }
}

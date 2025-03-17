package cc.yiueil.lang.instance;

/**
 * Parser 转换器门面 用于定义两个不同类型的转换规则
 * @param <T> 转换前的类
 * @param <E> 转换后的类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:59
 * @version 1.0
 */
@FunctionalInterface
public interface Parser<T, E> {
    /**
     * 将 T 转换为 E
     * @param t 转换前的类
     * @param e 转换后的类
     */
    void parse(T t, E e);
}

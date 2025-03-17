package cc.yiueil.lang.instance;

import javax.persistence.Id;

/**
 * HasId 有id的类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:59
 * @version 1.0
 * @param <T> 主键类型
 */
public interface HasId<T> {
    /**
     * 设置id
     * @param id id
     */
    void setId(T id);

    /**
     * 获取id
     * @return id
     */
    @Id
    T getId();
}

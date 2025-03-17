package cc.yiueil.lang.instance;

import javax.persistence.Column;

/**
 * HasOwn 拥有创建者类接口
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:00
 * @version 1.0
 * @param <T> 主键类型
 */
public interface HasOwn<T> {
    /**
     * 设置创建人id
     * @param id id
     */
    void setCreateUserId(T id);

    /**
     * 获取创建人id
     * @return id
     */
    @Column(name = "create_user", columnDefinition = "创建用户")
    T getCreateUserId();
}

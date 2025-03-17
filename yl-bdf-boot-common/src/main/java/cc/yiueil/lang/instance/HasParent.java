package cc.yiueil.lang.instance;

import javax.persistence.Column;

/**
 * HasParent 具有父子结构类接口
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:01
 * @version 1.0
 */
public interface HasParent<T> {
    /**
     * 设置parentId
     * @param parentId parentId
     */
    void setParentId(T parentId);

    /**
     * 获取parentId
     * @return parentId
     */
    @Column(name = "parent_id")
    T getParentId();
}

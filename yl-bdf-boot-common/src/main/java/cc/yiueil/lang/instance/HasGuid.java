package cc.yiueil.lang.instance;

import javax.persistence.Column;

/**
 * HasGuid guid 实体类的接口
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:59
 * @version 1.0
 */
public interface HasGuid {
    /**
     * 设置guid
     * @param guid guid
     */
    void setGuid(String guid);

    /**
     * 获取guid
     * @return guid
     */
    @Column(name = "guid", length = 32, columnDefinition = "Guid")
    String getGuid();
}

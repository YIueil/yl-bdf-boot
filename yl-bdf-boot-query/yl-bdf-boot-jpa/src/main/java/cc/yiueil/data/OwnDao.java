package cc.yiueil.data;

import cc.yiueil.lang.instance.HasOwn;

/**
 * OwnDao 提供设置创建人信息
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:33
 * @version 1.0
 */
public interface OwnDao {
    /**
     * 给对象注入创建人信息
     * @param entity 实体
     */
    default void generatorCreateUser(Object entity) {
        if (entity instanceof HasOwn ownEntity) {
            if(ownEntity.getCreateUserId() == null)
            {
                ownEntity.setCreateUserId(null);
            }
        }
    }
}

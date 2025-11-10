package cc.yiueil.data;

import cc.yiueil.lang.instance.HasTime;

import java.time.LocalDateTime;

/**
 * TimeDao 提供日期创建和修改方法
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:55
 * @version 1.0
 */
public interface TimeDao {
    /**
     * 生成创建时间
     * @param entity 实体
     */
    default void generatorCreateTime(Object entity) {
        if (entity instanceof HasTime timeEntity) {
            if (timeEntity.getCreateTime() == null) {
                timeEntity.setCreateTime(LocalDateTime.now());
            }
        }
    }

    /**
     * 更新修改时间
     * @param entity 实体
     */
    default void updateModifyTime(Object entity) {
        if (entity instanceof HasTime timeEntity) {
            timeEntity.setModifyTime(LocalDateTime.now());
        }
    }
}

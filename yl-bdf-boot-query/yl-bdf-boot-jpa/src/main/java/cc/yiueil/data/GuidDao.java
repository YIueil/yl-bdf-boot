package cc.yiueil.data;

import cc.yiueil.lang.instance.HasGuid;
import cc.yiueil.util.StringUtils;

import java.util.UUID;

/**
 * GuidDao 提供 guid 生成策略
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:32
 * @version 1.0
 */
public interface GuidDao {
    /**
     * 生成guid的方法
     * @param entity 实体
     */
    default void generatorGuid(Object entity) {
        if (entity instanceof HasGuid guidEntity) {
            if(StringUtils.isEmpty(guidEntity.getGuid()))
            {
                guidEntity.setGuid(UUID.randomUUID().toString().replace("-",""));
            }
        }
    }
}

package cc.yiueil.data;

import java.util.*;

/**
 * BatchDao 批处理dao,持有 批量保存,批量删除 功能
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:31
 * @version 1.0
 */
public interface BatchDao extends BaseDao{
    /**
     * 批量保存
     * @param entityList entity集合
     * @param <T> entity 类型
     */
    default <T> void batchSave(Collection<T> entityList) {
        for (T next : entityList) {
            save(next);
        }
    }

    /**
     * 批量删除
     * @param entityList entity集合
     */
    default void batchDelete(List<?> entityList) {
        for (Object object : entityList) {
            delete(object);
        }
    }
}

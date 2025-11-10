package cc.yiueil.data;

import java.util.Optional;

/**
 * BaseDao 基础dao, 持有 save delete 基础功能
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:31
 * @version 1.0
 */
public interface BaseDao {
    /**
     * 基本查询
     * @param <T> entity类型
     * @param tClass entity类型
     * @param id id
     * @return 查询结果
     */
    <T> Optional<T> findById(Class<T> tClass, Object id);

    /**
     * 通过 guid 查找 bean
     * @param tClass 目标类型
     * @param guid bean 的 guid
     * @param <T> 查找的目标类型
     * @return Optional
     */
    <T> Optional<T> findByGuid(Class<T> tClass, String guid);

    /**
     * 基本保存、更新
     * @param entityObject entity对象
     * @return T
     */
    <T> T save(T entityObject);

    /**
     * 基本删除
     * @param entityObject entity对象
     */
    void delete(Object entityObject);

    /**
     * 基本删除
     * @param id id
     * @param <T> 删除目标类型
     * @param tClass 删除目标类型
     */
    @Deprecated
    <T> void deleteById(Class<T> tClass, Object id);

    /**
     * 通过guid进行删除
     * @param guid guid
     * @param <T> 删除目标类型
     * @param tClass 删除目标类型
     */
    <T> void deleteByGuid(Class<T> tClass, String guid);
}

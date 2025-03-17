package cc.yiueil.lang.instance;

/**
 * BaseEntity 基本实体 包括id, guid, createTime, modifyTime, createUser属性
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:59
 * @version 1.0
 * @param <T> 主键类型
 */
public interface BaseEntity<T> extends HasId<T>, HasGuid, HasTime, HasOwn<T> {
}

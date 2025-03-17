package cc.yiueil.lang.instance;

import java.io.Serializable;

/**
 * Node 节点门面 作为一个节点具备的标准属性包含了 id, name, parentId, weight. 并且具备根据 weight 进行排序的功能
 * @param <T> id类型
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:56
 * @version 1.0
 */
public interface Node<T> extends Comparable<Node<T>>, Serializable {

    /**
     * 获取ID
     *
     * @return ID
     */
    T getId();

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    Node<T> setId(T id);

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    T getParentId();

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     * @return this
     */
    Node<T> setParentId(T parentId);

    /**
     * 获取节点标签名称
     *
     * @return 节点标签名称
     */
    CharSequence getName();

    /**
     * 设置节点标签名称
     *
     * @param name 节点标签名称
     * @return this
     */
    Node<T> setName(CharSequence name);

    /**
     * 获取权重
     *
     * @return 权重
     */
    Comparable<?> getWeight();

    /**
     * 设置权重
     *
     * @param weight 权重
     * @return this
     */
    Node<T> setWeight(Comparable<?> weight);


    /**
     * todo 实现按权限排序
     * @param node 要进行比较的节点
     * @return 比较结果
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    default int compareTo(Node node) {
        if(null == node){
            return 1;
        }
        final Comparable weight = this.getWeight();
        final Comparable weightOther = node.getWeight();
        return weight.compareTo(weightOther);
    }
}
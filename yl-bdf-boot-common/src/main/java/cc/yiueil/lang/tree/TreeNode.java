package cc.yiueil.lang.tree;

import cc.yiueil.lang.instance.Node;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * TreeNode 树形结构封装类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:12
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode<T> implements Node<T>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点id
     */
    private T id;
    /**
     * 父节点id
     */
    private T parentId;
    /**
     * 节点名
     */
    private CharSequence name;
    /**
     * 权重(默认0)
     */
    private Comparable<?> weight = 0;
    /**
     * 扩展字段
     */
    private Map<String, Object> extra;

    @Override
    public TreeNode<T> setId(T id) {
        this.id = id;
        return this;
    }

    @Override
    public T getId() {
        return this.id;
    }

    @Override
    public T getParentId() {
        return this.parentId;
    }

    @Override
    public TreeNode<T> setParentId(T parentId) {
        this.parentId = parentId;
        return this;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public TreeNode<T> setName(CharSequence name) {
        this.name = name;
        return this;
    }

    @Override
    public Comparable<?> getWeight() {
        return weight;
    }

    @Override
    public TreeNode<T> setWeight(Comparable<?> weight) {
        this.weight = weight;
        return this;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public TreeNode<T> setExtra(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }
}

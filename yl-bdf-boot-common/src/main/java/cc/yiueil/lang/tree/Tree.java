package cc.yiueil.lang.tree;

import cc.yiueil.lang.instance.Node;
import cc.yiueil.util.ArrayUtils;
import cc.yiueil.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Tree
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:12
 * @version 1.0
 */
public class Tree<T> extends LinkedHashMap<String, Object> implements Node<T> {
	private static final long serialVersionUID = 1L;

	private final TreeNodeConfig treeNodeConfig;

	public Tree(TreeNodeConfig config) {
		this.treeNodeConfig = ObjectUtils.defaultIfNull(config, TreeNodeConfig.DEFAULT_CONFIG);
	}

	public TreeNodeConfig getTreeNodeConfig() {
		return treeNodeConfig;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getId() {
		return (T) get(treeNodeConfig.getIdKey());
	}

	@Override
	public Node<T> setId(T id) {
		put(treeNodeConfig.getIdKey(), id);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getParentId() {
		return (T) get(treeNodeConfig.getParentIdKey());
	}

	@Override
	public Node<T> setParentId(T parentId) {
		this.put(treeNodeConfig.getParentIdKey(), parentId);
		return this;
	}

	@Override
	public CharSequence getName() {
		return (CharSequence) get(treeNodeConfig.getNameKey());
	}

	@Override
	public Tree<T> setName(CharSequence name) {
		this.put(treeNodeConfig.getNameKey(), name);
		return this;
	}

	@Override
	public Comparable<?> getWeight() {
		return (Comparable<?>) get(treeNodeConfig.getWeightKey());
	}

	@Override
	public Tree<T> setWeight(Comparable<?> weight) {
		this.put(treeNodeConfig.getWeightKey(), weight);
		return this;
	}

	/**
	 * 扩展属性
	 *
	 * @param key   键
	 * @param value 扩展值
	 */
	public void putExtra(String key, Object value) {
		this.put(key, value);
	}

	/**
	 * 获取所有子节点
	 *
	 * @return 所有子节点
	 */
	@SuppressWarnings("unchecked")
	public List<Tree<T>> getChildren() {
		return (List<Tree<T>>) this.get(treeNodeConfig.getChildrenKey());
	}

	/**
	 * 设置子节点，设置后会覆盖所有原有子节点
	 *
	 * @param children 子节点列表，如果为{@code null}表示移除子节点
	 */
	public void setChildren(List<Tree<T>> children) {
		if(null == children){
			this.remove(treeNodeConfig.getChildrenKey());
		}
		this.put(treeNodeConfig.getChildrenKey(), children);
	}

	/**
	 * 增加子节点，同时关联子节点的父节点为当前节点
	 *
	 * @param children 子节点列表
	 */
	@SafeVarargs
	public final void addChildren(Tree<T>... children) {
		if (ArrayUtils.isNotEmpty(children)) {
			List<Tree<T>> childrenList = this.getChildren();
			if (null == childrenList) {
				childrenList = new ArrayList<>();
				setChildren(childrenList);
			}
			childrenList.addAll(Arrays.asList(children));
		}
	}
}